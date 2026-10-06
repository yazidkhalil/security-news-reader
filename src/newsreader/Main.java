package newsreader;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Main {

	/**
	 * Downloads the RSS feed from the given URL.
	 * 
	 * @param url the RSS feed URL
	 * @return the feed as XML text
	 * @throws Exception if the request fails
	 */
	private static String getFeed(String url) throws Exception {
		URI uri = URI.create(url);
		HttpClient http = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(uri).build();
		HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();

	}

	/**
	 * Creates an XML tree, and prints each article that contains the keyword
	 * entered by the user.
	 * 
	 * @param xml     the RSS feed as XML text
	 * @param keyword the user's keyword
	 * @throws Exception if the XML can't be parsed
	 */
	private static void printArticles(String xml, String keyword) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		NodeList articles = doc.getElementsByTagName("item");

		int i = 0;
		while (i < articles.getLength()) {

			Element article = (Element) articles.item(i);
			String title = getTagText(article, "title");
			String description = getTagText(article, "description");
			String link = getTagText(article, "link");
			String pubDate = getTagText(article, "pubDate");
			String author = getTagText(article, "author");

			if (containsKeyword(title, description, keyword)) {
				System.out.println("Title : " + title);
				System.out.println("Description: " + description);
				System.out.println("Link: " + link);
				System.out.println("Publish date: " + pubDate);
				System.out.println("Author: " + author);
				System.out.println();
			}
			i++;
		}
	}

	/**
	 * Gets the text of the given tag inside an article.
	 * 
	 * @param article the article to search in
	 * @param tag     the tag name to look for
	 * @return the tag's text if it exists or "N/A" if it doesn't.
	 */
	private static String getTagText(Element article, String tag) {
		NodeList list = article.getElementsByTagName(tag);
		if (list.getLength() > 0) {
			return list.item(0).getTextContent();
		} else {
			return "N/A";
		}
	}

	/**
	 * Checks if the article contains the keyword inserted by the user.
	 * 
	 * @param title       article's title
	 * @param description article's description
	 * @param keyword     The keyword to look for in article
	 * @return true if keyword exists, or returns false if it doesn't.
	 */
	private static boolean containsKeyword(String title, String description, String keyword) {

		if (title.toLowerCase().contains(keyword.toLowerCase())
				|| description.toLowerCase().contains(keyword.toLowerCase())) {
			return true;
		} 
		return false;
	}

	/**
	 * Asks the user to enter an RSS feed URL and prints its articles.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {

		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter RSS Feed URL: ");
			String userInput = input.nextLine();
			System.out.println("Enter a keyword: ");
			String userKeyword = input.nextLine();
			// https://feeds.feedburner.com/TheHackersNews (Test link)
			// https://krebsonsecurity.com/feed/ (Test link)
			String xml = getFeed(userInput);
			printArticles(xml, userKeyword);
		} catch (Exception e) {
			// System.out.println("URL Link invalid.");
			e.printStackTrace();
		}

	}
}
