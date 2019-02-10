package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
			// TODO scrapare i tag da qua https://www.photoawards.com/categories/
			File input = new File("D:\\Documenti\\Progetto MAP\\file.html");
			FileWriter foto_file = new FileWriter(new File("src/main/resources/foto.txt"));
			foto_html = Jsoup.parse(input, "UTF-8", "https://images.nasa.gov/");
			Elements links = foto_html.getElementsByTag("img");
			for (Element link : links) {
				if (!"".equals(link.attr("ng-src")))
					foto_file.write(link.attr("ng-src") + " ||| " + link.attr("alt") + "\n");
			}
			foto_file.close();
			/*
			 * FileWriter tag_file = new FileWriter( new
			 * File("src/main/resources/foto.txt")); foto_html =
			 * Jsoup.connect("https://www.photoawards.com/categories/").get(); Elements
			 * links = foto_html.
			 * select("body > div.wrapper > div > div > div > div.full_width > div > div.vc_row.wpb_row.section.vc_row-fluid.white-page.grid_section > div > div > div > div > div > div:nth-child(2) > div.wpb_column.vc_column_container.vc_col-sm-8 > div > div > div.wpb_text_column.wpb_content_element > div > ul"
			 * ); Set<String> tags = new HashSet<String>(); for (Element link : links) {
			 * for(Element yolo: link.getElementsByTag("li")) {
			 * tags.add(yolo.child(0).text()); } } for(String t: tags) {
			 * tag_file.write(t+"\n"); } tag_file.close();
			 */

			// NOMI
			/*
			 * FileWriter nomi_file = new FileWriter( new File(
			 * "C:\\Users\\armi9\\git\\map-unipa\\galleria\\src\\main\\resources\\nomi.txt")
			 * ); nomi_html = Jsoup.connect(
			 * "https://www.behindthename.com/names/browse.php?type_gender=1&operator_gender=is&value_gender%5B%5D=unisex&type_popularity=1&operator_popularity=is&value_popularity%5B%5D=&yearoperator_popularity=is&year_popularity%5B%5D=&rankoperator_popularity=is&rank_popularity%5B%5D=")
			 * .get(); tmp = nomi_html.getElementsByAttributeValue("class", "listname"); for
			 * (Element a : tmp) { nomi_file.write(a.childNode(0).childNode(0).toString() +
			 * "\n"); } nomi_file.close(); // COGNOMI FileWriter cognomi_file = new
			 * FileWriter( new File(
			 * "C:\\Users\\armi9\\git\\map-unipa\\galleria\\src\\main\\resources\\cognomi.txt"
			 * )); cognomi_html = Jsoup.connect("https://en.geneanet.org/genealogy/").get();
			 * tmp = cognomi_html.select("#noms > li > a"); for (Element a : tmp) {
			 * cognomi_file.write(a.childNode(0).toString() + "\n"); } cognomi_file.close();
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
