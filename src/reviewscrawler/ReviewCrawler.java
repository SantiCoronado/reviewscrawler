package reviewscrawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class ReviewCrawler
{
	public static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	//Create sets for all URL of categories, subcategories and reviews
	Set<String> categoryLinks =  new HashSet<String>();
	Set<String> subcategoryLinks=  new HashSet<String>();
	Set<String> reviewLinks = new HashSet<String>();
	Set<String> reviewValues = new HashSet<String>();
	
	String category = new String();
	String productName = new String();
	String ratingValue = new String();
	String maxValue = new String();
	String numberReviews = new String();
	
	public static Document connectTo(String url) throws IOException
	{
		Document doc = Jsoup.connect(url).userAgent(USER_AGENT).ignoreHttpErrors(true).
				timeout(1000*5).get();
		return doc;
	}
	
	
}
