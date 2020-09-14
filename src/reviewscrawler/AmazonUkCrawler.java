package reviewscrawler;

import java.io.IOException;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


class AmazonUkCrawler extends AmazonCrawler
{

	// Root connection to the starting URL (electronics) to crawl categories from Amazon.co.uk
	String electronicCategory = new String ("https://www.amazon.co.uk/electronics-camera-mp3-ipod-tv/b/ref=sd_allcat_el?ie=UTF8&node=560798");
	
	
	
		
	Set<String> getCategoryLinks() throws IOException
	{
		Document amazonUkConnect = connectTo(electronicCategory);
				
		Elements categoryElements = amazonUkConnect.select("ul[class=a-unordered-list a-nostyle a-vertical s-ref-indent-one]");
		Elements linksCategory = categoryElements.select("a[href]");
		int size = linksCategory.size();
		for (int i = 0; i < size; i++)
		{
			categoryLinks.add(linksCategory.get(i).attr("abs:href"));
		}
			
			
		return categoryLinks;
	}
			
		
	
	//HELPER METHODS
	
	
	//Gets date when product was added
	String getProductDate(Document doc) throws IOException
	{
		String productDatePre = doc.select("li:contains(Date first available)").text();
		String productDate = productDatePre.substring((productDatePre.indexOf(":")+2));
		return productDate;
	}
	
	
	//Gets the URL for the verified reviews of a product with the old page structure	
	String getVerifiedLinkOldStruc(Document doc) throws IOException
	{
			
		String verifiedLinkPre = doc.select("span[class=crAvgStars] > a").attr("href");
		String verifiedLink = verifiedLinkPre + "&reviewerType=avp_only_reviews&pageNumber=1";
		return verifiedLink;
	}
	
	//Gets ASIN number
	String getAsinNumber(Document doc) throws IOException
	{
		String asin = doc.select("li:contains(ASIN:)").text();
		asinNumber = asin.substring((asin.indexOf(":")+2));
		return asinNumber;
	}
	
	//Gets model number
	String getModelNumber(Document doc) throws IOException
	{
		//Checks that model information was entered for the product
		String model = new String();
		if (doc.select("li:contains(Item model number:)").size() > 0)
		{
			model = doc.select("li:contains(Item model number:)").get(0).text();
			modelNumber = model.substring((model.indexOf(":")+2));
		}
		else
		{
			modelNumber = "No model information";
		}
		return modelNumber;	
	}
		

	// END OF HELPER METHODS	
		
		

	Set<String> getReviewValues() throws IOException
	{
		// For loop to iterate all the crawled review links
		//for (String reviewLink : reviewLinks)
		//{
			
		// URL to scrap review values from a specific product from Amazon.co.uk
		String productUrl = new String ("https://www.amazon.co.uk/Samsung-Galaxy-32GB-SIM-Free-Smartphone/dp/B01C5OIIF2/ref=sr_1_4?s=electronics&ie=UTF8&qid=1473649868&sr=1-4&keywords=samsung+galaxy+s7");
		Document reviewPage = connectTo(productUrl);		
		
		
		Boolean isReviewRated = new Boolean (reviewPage.select("span[class=asinReviewsSummary]").size() > 0);
		
		if (!isReviewRated)
		{
			reviewValues.add("No reviews on this product");
			return reviewValues;
		}

		
		else
		{
			
		
			//Get the values from taking different elements depending on the page structure	
			//Condition to parse as the new page structure
			Boolean isNewStructure = reviewPage.select("span[id=acrPopover]").size() > 0;
			if(isNewStructure)
			{
				ratingValuePre = reviewPage.select("span[id=acrPopover]").attr("title");
				ratingValue = ratingValuePre.substring((0), (ratingValuePre.indexOf("o")-1));
				numberReviews = reviewPage.select("span[id=acrCustomerReviewText]").text();
				productName = reviewPage.select("span[id=productTitle]").text();
				// Condition to make sure that there is price
				if (reviewPage.select("span[id=priceblock_ourprice]").size() > 0)
				{
					price = reviewPage.select("span[id=priceblock_ourprice]").text();
				}
				else
				{
					price = "No price is set";
				}
				//Gets the URL for the verified reviews of a product
				verifiedReviewsLink = getVerifiedLinkNewStruc(reviewPage);
			}
			
			//Condition to parse as the old page structure
			Boolean isOldStructure = new Boolean (reviewPage.select("div[class=gry txtnormal acrRating]").size() > 0);
			if (isOldStructure)	
			{
				ratingValuePre = reviewPage.select("div[class=gry txtnormal acrRating]").text();
				ratingValue = ratingValuePre.substring((0), (ratingValuePre.indexOf("o")-1));
				numberReviews = reviewPage.select("div[class=fl gl5 mt3 txtnormal acrCount]").text();
				productName = reviewPage.select("span[id=btAsinTitle]").text();
				// Condition to make sure that there is price
				if (reviewPage.select("span[class=olpCondLink] > span").size() > 0)
				{
					price = reviewPage.select("span[class=olpCondLink] > span").first().text();
				}
				
				//Gets the URL for the verified reviews of a product
				verifiedReviewsLink = getVerifiedLinkOldStruc(reviewPage);
			}
		
			//Get the maximum rate value possible
			maxValue = getMaxValue(reviewPage);
			
			//Get product's category
			category = getProductCategory(reviewPage);
		
			//Get model number
			modelNumber = getModelNumber(reviewPage);
		
			//Get ASIN number
			asinNumber = getAsinNumber(reviewPage);
		
			//Get product date
			productDate = getProductDate(reviewPage);

			
		
			//Gets a list with all the values for the verified reviews of a product
			verifiedReviewValues = getVerifiedReviewValues(verifiedReviewsLink);
		
			// Calculate average (mean) of all the verified review values
			verifiedReviewResult = getVerifiedAverage(verifiedReviewValues);
		
		
			//Return all the values from the review
			reviewValues.add("\n" + "Category: " + category + ",\n" + "Product: " + productName + ",\n" +
				"Model: " + modelNumber + ", " + "ASIN: " + asinNumber + ", " + "Price: " + price +
					", " + "Available from: " + productDate + ",\n" + "Rating value: " + ratingValue + " out of " +
					maxValue + ", Total of " + numberReviews + ",\n" + "Rating value in verified reviews: " +
					verifiedReviewResult + ",");
		}
		//}
		return reviewValues;
	}



}
