package reviewscrawler;

import java.io.IOException;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



class AmazonUsCrawler extends AmazonCrawler
{
	
	// Root connection to the starting URL to crawl categories from Amazon.co.uk
	String rootAmazonUs = new String ("https://www.amazon.com/electronics-store/b/ref=sd_allcat_elec_hub?ie=UTF8&node=172282");
	String priceGbp = new String();
	
	
	
	Set<String> getCategories() throws IOException
	{
		Document amazonUsconnect = connectTo(rootAmazonUs);
		
		Elements links = amazonUsconnect.select("div [class=left_nav browseBox] > ul > li > a[href]");
			
		int size = links.size();
		for (int i = 0; i < size; i++)
		{
			categoryLinks.add(links.get(i).attr("abs:href"));
		}
			return categoryLinks;
	}
	
	//Gets ASIN number
	String getAsinNumber(Document doc) throws IOException
	{
		String asin = doc.select("tr:contains(ASIN)").text();
		asinNumber = asin.substring((asin.indexOf(":")+6));
		return asinNumber;
	}
	
	
	//Gets model number
	String getModelNumber(Document doc) throws IOException
	{
		//Checks that model information was entered for the product
		String model = new String();
		if (doc.select("tr:contains(Item model number)").size() > 0)
		{
			model = doc.select("tr:contains(Item model number)").get(0).text();
			modelNumber = model.substring((model.indexOf("r")+2));
		}
		else
		{
			modelNumber = "No model information";
		}
		return modelNumber;	
	}
	
	//Gets the URL for the verified reviews of a product with the new page structure	
	String getVerifiedLinkNewStruc(Document doc) throws IOException
	{
				
		String verifiedLink = doc.select("a[id=dp-summary-see-all-reviews]").attr("abs:href");
		return verifiedLink;
	}
	
	
	
	Set<String> getReviewValues() throws IOException
	{
		String productUrl = new String ("https://www.amazon.com/Kata-C2-5-5-inch-International-Smartphone/dp/B01NBDO1TV/ref=sr_1_458?s=wireless&ie=UTF8&qid=1499689686&sr=1-458");
		Document reviewPage = connectTo(productUrl);		
		
		
		ratingValuePre = reviewPage.select("span[id=acrPopover]").attr("title");
		ratingValue = ratingValuePre.substring((0), (ratingValuePre.indexOf("o")-1));
		numberReviews = reviewPage.select("span[id=acrCustomerReviewText]").text();
		productName = reviewPage.select("span[id=productTitle]").text();
		price = reviewPage.select("span[id=priceblock_dealprice]").text();
		
		//Get the maximum rate value possible
		maxValue = getMaxValue(reviewPage);
		
		//Get product's category
		category = getProductCategory(reviewPage);
	
		//Get model number
		modelNumber = getModelNumber(reviewPage);
	
		//Get ASIN number
		asinNumber = getAsinNumber(reviewPage);
		
		verifiedReviewsLink = getVerifiedLinkNewStruc(reviewPage);
		
		//Gets a list with all the values for the verified reviews of a product
		verifiedReviewValues = getVerifiedReviewValues(verifiedReviewsLink);
	
		// Calculate average (mean) of all the verified review values
		verifiedReviewResult = getVerifiedAverage(verifiedReviewValues);
		
		priceGbp = CurrencyConverter.convertUsdToGbp(price);
		
		
		
		
		reviewValues.add("\n" + "Category: " + category + ",\n" + "Product: " + productName + ",\n" +
				"Model: " + modelNumber + ", " + "ASIN: " + asinNumber + ", " + ",\n" + "Price: " + price +
					", Price in GBP: " + priceGbp + ",\n" + "Rating value: " + ratingValue + " out of " +
					maxValue + ", Total of " + numberReviews + ",\n" + "Rating value in verified reviews: " +
					verifiedReviewResult);
		
		return reviewValues;
	}
	
	
	
	
	
	
	

}
