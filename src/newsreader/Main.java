package newsreader;

import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {

	private static String getFeed(String url) throws Exception{
		URI uri = URI.create(url);
		HttpClient http =  HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(uri).build();
		HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
	 		return response.body();

	}
		

	
	
	
	
	public static void main(String[] args)  {
		
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter RSS Feed URL: ");
			String userInput = input.nextLine();
			//https://feeds.feedburner.com/TheHackersNews
			String xml = getFeed(userInput);
			System.out.println(xml);
		} catch(Exception e) {
			System.out.println("URL Link invalid.");
		}
		
		
	}
}



