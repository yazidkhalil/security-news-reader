# Security News Reader

A Java program that reads RSS feeds from security news websites and filters articles by keywords.

## Features
- Asks the user for an RSS feed URL and a keyword
- Downloads the RSS feed from the URL
- Filters articles by the keyword 
- Creates an HTML file called `security-news.html`


## How to Run
Requires Java 11 or newer.
1. Open the project in Eclipse (or any Java IDE)
2. Run `Main.java`
3. Type the RSS Feed URL you want and a keyword you want filtered.
4. Open the `security-news.html` file

## Example Feeds
- https://feeds.feedburner.com/TheHackersNews 
- https://krebsonsecurity.com/feed/


## Built With 
- Java
- HttpClient
- DocumentBuilder