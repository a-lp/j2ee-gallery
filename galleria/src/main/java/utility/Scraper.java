package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static void main(String[] args) {
		Document foto_html;
		Document nomi_html;
		Document cognomi_html;
		Elements tmp;
		try {
			// FOTO
			File input = new File("D:\\Documenti\\Progetto MAP\\file.html");
			FileWriter foto_file = new FileWriter(
					new File("C:\\Users\\armi9\\git\\map-unipa\\galleria\\src\\main\\resources\\foto.txt"));
			foto_html = Jsoup.parse(input, "UTF-8", "https://images.nasa.gov/");
			Elements links = foto_html.getElementsByTag("img");
			for (Element link : links) {
				if (!"".equals(link.attr("ng-src")))
					foto_file.write(link.attr("ng-src") + "|||" + link.attr("alt") + "\n");
			}
			foto_file.close();
			// NOMI
			FileWriter nomi_file = new FileWriter(
					new File("C:\\Users\\armi9\\git\\map-unipa\\galleria\\src\\main\\resources\\nomi.txt"));
			nomi_html = Jsoup.connect(
					"https://www.behindthename.com/names/browse.php?type_gender=1&operator_gender=is&value_gender%5B%5D=unisex&type_popularity=1&operator_popularity=is&value_popularity%5B%5D=&yearoperator_popularity=is&year_popularity%5B%5D=&rankoperator_popularity=is&rank_popularity%5B%5D=")
					.get();
			tmp = nomi_html.getElementsByAttributeValue("class", "listname");
			for (Element a : tmp) {
				nomi_file.write(a.childNode(0).childNode(0).toString() + "\n");
			}
			nomi_file.close();
			// COGNOMI
			FileWriter cognomi_file = new FileWriter(
					new File("C:\\Users\\armi9\\git\\map-unipa\\galleria\\src\\main\\resources\\cognomi.txt"));
			cognomi_html = Jsoup.connect("https://en.geneanet.org/genealogy/").get();
			tmp = cognomi_html.select("#noms > li > a");
			for (Element a : tmp) {
				cognomi_file.write(a.childNode(0).toString() + "\n");
			}
			cognomi_file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
