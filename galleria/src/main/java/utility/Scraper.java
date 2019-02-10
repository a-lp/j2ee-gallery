package utility;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static void main(String[] args) {
		//Scraping immagini dalla NASA
		Document doc;
		try {
			File input = new File("D:\\Documenti\\Progetto MAP\\file.html");
			doc = Jsoup.parse(input, "UTF-8","https://images.nasa.gov/");
			Elements links = doc.getElementsByTag("img");
			for(Element link: links) {		
				if(!"".equals(link.attr("ng-src")))
					System.out.println(link.attr("ng-src")+"\n"+link.attr("alt"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
