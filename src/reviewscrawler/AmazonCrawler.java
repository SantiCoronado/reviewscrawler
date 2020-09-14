package reviewscrawler;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonCrawler extends ReviewCrawler
{

	String modelNumber = new String();
	String asinNumber = new String();
	String price = new String();
	String productDate = new String();
	String ratingValuePre = new String();
	String verifiedReviewsLink = new String();
	String verifiedReviewResult = new String();
	
	int i = new Integer(0);
	int page = new Integer(0);
	
	List<String> verifiedReviewValues = new ArrayList<String>();
	
		
	
	Set<String> getSubcategoryLinks(Set<String> categories) throws IOException
	{
		//TEST URL for Amazon UK. This is a URL for "Accesories" category. It should crawl 13 subcategories
		//String accessoryCategory = new String("https://www.amazon.co.uk/s/ref=lp_560798_nr_n_0?fst=as%3Aoff&rh=n%3A560798%2Cn%3A%21560800%2Cn%3A1345741031&bbn=560800&ie=UTF8&qid=1499268301&rnid=560800");
			
		for (String eachCategoryLink : categoryLinks)
		{
			Document categoriesConnect = connectTo(eachCategoryLink);
			
			//Elements links = categoriesConnect.select("div[class=categoryRefinementsSection] > ul > li[style]> a[href]");
			Elements subcategoryElements = categoriesConnect.select("ul[class=a-unordered-list a-nostyle a-vertical s-ref-indent-two]");
			Elements linksSubcategory = subcategoryElements.select("a[href]");
			
			int size = linksSubcategory.size();
			for (int i = 0; i < size; i++)
			{
				
				subcategoryLinks.add(linksSubcategory.get(i).attr("abs:href"));
				
			}
		}
			return subcategoryLinks;
	}
		
	
	//In order to crawl the whole site, the argument should be changed to linkcategory set. For testing purposes
	//an string argument has been added, so just a single category can be crawled.
		
	Set<String> getReviewLinks(String connectToUrl) throws IOException
	{
				
		Document reviewsConnect = connectTo(connectToUrl);
		Elements links = reviewsConnect.select("li[data-asin] > div > div > div > a[href]");
		//Iterate as many products are in this page
		int size = links.size();
		for (int i = 0; i < size; i++)
		{
			Boolean linkEndsDigit = new Boolean (Character.isDigit((links.get(i).attr("abs:href").charAt((links.get(i).attr("abs:href").length()-1)))));
			Boolean linkOffer = new Boolean (links.get(i).attr("href").contains("offer"));
			if(linkEndsDigit && !linkOffer)
			{
				reviewLinks.add(links.get(i).attr("abs:href"));
			}
			
		}
		
		if ((reviewsConnect.select("a[title=Next Page]").attr("abs:href").length() > 0))
		{
			String nextpage = reviewsConnect.select("a[title=Next Page]").attr("abs:href");
			System.out.println("A page has been crawled");
			getReviewLinks(nextpage);
		}
		
		return reviewLinks;
	}
	
	
	//Gets the maximum rate value possible
	String getMaxValue(Document doc) throws IOException
	{
		maxValue = ratingValuePre.substring((ratingValuePre.indexOf("f")+2), ratingValuePre.indexOf("s")-1);
		return maxValue;
	}
		
	
	//Get product's category
	String getProductCategory(Document doc) throws IOException
	{
		category = doc.select("div[id=wayfinding-breadcrumbs_feature_div]").get(0).text();
		return category;
	}
		
		
	//Gets the URL for the verified reviews of a product with the new page structure	
	String getVerifiedLinkNewStruc(Document doc) throws IOException
	{
			
		String verifiedLinkPre = doc.select("a[class=a-link-emphasis a-nowrap]").attr("href");
		// Show only verified purchases
		String verifiedLink = verifiedLinkPre.replace("Type=all_reviews", "Type=avp_only_reviews");
		return verifiedLink;
	}
			
	//Gets a list with all the values for the verified reviews of a product
	List<String> getVerifiedReviewValues(String connectToUrl) throws IOException
	{
		String nextpage = new String();
		// Connects to the verified reviews page	
		Document verifiedReviewsconnect = connectTo(connectToUrl);
		// Select the value of each verified review
		Elements links = verifiedReviewsconnect.
				select("div[id=cm_cr-review_list] > div[id] > div[id] > div[class] > a[class=a-link-normal] > i");
		
		//System.out.println("working...");
		
		// Adds the value of each verified review to a list
		int size = links.size();
		for (int i = 0; i < size; i++)
		{
			String stars = links.get(i).attr("class");
			String starsNumber = stars.replaceAll("\\D+","");
			verifiedReviewValues.add(starsNumber);
		}
			
		// while loop to test an N number of verified review pages	
		//while (page < 1)
		//{
			
		// Try to reach a next page and if found, repeat from the beginning of the method
		if ((verifiedReviewsconnect.select("li[class=a-last] > a").attr("abs:href").length() > 0))
		{
			nextpage = verifiedReviewsconnect.select("li[class=a-last] > a").attr("abs:href");
			page++;
			getVerifiedReviewValues(nextpage);
		}
		return verifiedReviewValues;
	}	
	
	//Gets a String with the mean of all the verified review values of a product
	String getVerifiedAverage(List<String> verifiedReviewValues) throws IOException
	{
		double totalVerStars = new Integer(0);
		// Add mathematically all the values from the list in totalVerStars
		int size = verifiedReviewValues.size();
		for (int i = 0; i < size; i++)
		{
			int intStar = Integer.parseInt(verifiedReviewValues.get(i));
			totalVerStars = totalVerStars + intStar;
		}
			
		// Calculate average (mean) of all the verified review values
		double avgVerStars = totalVerStars / size;
		DecimalFormat df = new DecimalFormat("#.#");
		String avgResult = df.format(avgVerStars) + " out of " + maxValue + ", " +
		"Total of " + size + " customer reviews";
			
		return avgResult;
	}
		
	
}
