package reviewscrawler;

import java.io.IOException;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class TechradarCrawler extends ReviewCrawler
{

	
	
	// Root connection to the starting URL to crawl reviews from Techradar.com
	String rootTechradar = new String ("http://www.techradar.com/reviews");
	
	Set<String> getCategoryLinks() throws IOException 
	{
		Document techradarconnect = connectTo(rootTechradar);
		
		Elements links = techradarconnect.select("a[href]");
		
		// Test 0: the data collection of each review by limiting to ten results
		
		int size = links.size();
		for (int i = 0; i < size; i++) 
		{
			
			Boolean isUsefulLink = new Boolean (links.get(i).attr("href").contains("techradar.com/review"));
			Boolean isReviewLink = new Boolean (links.get(i).attr("href").endsWith("review"));
			Boolean isCategoryLink = new Boolean (!links.get(i).attr("href").endsWith("reviews"));
			
			if(isUsefulLink)
			{
				if(isReviewLink)
				{
			
				// Add it to the list of links of reviews
				reviewLinks.add(links.get(i).attr("href"));
				}
				
				// Add as category
				else if(isCategoryLink)
				{
					categoryLinks.add(links.get(i).attr("href"));
				}
			}
		}
		return categoryLinks;
	}
		
		
		
	Set<String> getSubcategoryLinks() throws IOException
	{
		// Iterate through the list of categories and connect to each category's URL
		for (String eachCategoryLink : categoryLinks)
		{
			String test1 = new String("http://www.techradar.com/reviews/gaming");
			// TEST 1: Exchange the URL where the document is connected "eachCategoryLink" to "http://www.techradar.com/reviews/wearables"
			//  or "http://www.techradar.com/reviews/gaming" for testing the code and don't crawl all the site.
			Document categoriesConnect = connectTo(test1);
			Elements links = categoriesConnect.select("a[href]");
			
			// Add any new links that will contain reviews' URLs into a list
			// Techradar structure locates the reviews organised by brand or subcategories,
			// therefore these links will be labelled as "subcategoryLinks" 
			
			int size = links.size();
			for (int i = 0; i < size; i++)
			{
				
				// Only add the URL if it contains the same pattern as the category
				Boolean isSubcategoryLink = new Boolean (links.get(i).absUrl("href").contains(eachCategoryLink));
				if(isSubcategoryLink)
				{
					subcategoryLinks.add(links.get(i).absUrl("href"));
					}
				}
			
	
		}
		return subcategoryLinks;
		}

		Set<String> getReviewLinks() throws IOException
		{
		// Iterate through the list of subcategories and connect to each subcategory's URL
			
		// Mark as comment lines the following code to test and reduce the crawling time
			
			for (String eachSubcategoryLink : subcategoryLinks)
			{
				Document subcategoriesConnect = connectTo(eachSubcategoryLink);
				Elements links = subcategoriesConnect.select("a[href]");
							
				// Iterate through each subcategory element
				int size = links.size();
				for (int i = 0; i < size; i++)
				{							
					// Add any correctly formed URLs into a list if they end with "review"
					Boolean isCorrectUrl = new Boolean (links.get(i).absUrl("href").startsWith("http://www."));
					Boolean isReviewLink = new Boolean (links.get(i).attr("href").endsWith("review"));
					
					if(isCorrectUrl && isReviewLink)
					{
						reviewLinks.add(links.get(i).absUrl("href"));
					}
				}
			}
			return reviewLinks;
		}
		
		
		//HELPER METHODS
		
		private String getProductCategory(Document doc) throws IOException
		{
			//Get product's category
			category = doc.select("a[class=chunk]").text();
			return category;
		}
		
		private String getProductName(Document doc) throws IOException
		{
			//Get product's name
			productName = doc.select("meta[itemprop=name]").attr("content");
			return productName;
		}
		
		private String getProductRating(Document doc) throws IOException
		{
			//Get the rating value attribute
			ratingValue = doc.select("meta[itemprop=ratingValue]").attr("content");
			return ratingValue;
		}
		
		private String getMaxValue(Document doc) throws IOException
		{
			//Get the maximum rate value possible
			maxValue = doc.select("meta[itemprop=bestRating]").attr("content");
			return maxValue;
		}
		
		private String getProductDate(Document doc) throws IOException
		{
			//Get date of review
			String dateReview = doc.select("time[itemprop=datePublished]").attr("datetime");
			String dateFormatted = dateReview.substring(0, 10);
			
			return dateFormatted;
		}
		
		
		
		Set<String> getReviewValues() throws IOException
		{
				
				
		// Iterate through the list of reviews URLs and connect to each one of them
			for (String reviewLink : reviewLinks)
			{
				Document reviewPage = connectTo(reviewLink);
				
				// Condition to check that the review has been rated
				Boolean isReviewRated = new Boolean (reviewPage.select("meta[itemprop=ratingValue]").size() > 0);
				if (isReviewRated)
				{
				//Get product's category
				category = getProductCategory(reviewPage);
				//Get product name
				productName = getProductName(reviewPage);
				//Get the rating value attribute
				ratingValue = getProductRating(reviewPage);							
				//Get the maximum rate value possible
				maxValue = getMaxValue(reviewPage);						
				//Get date of review
				String dateReview = getProductDate(reviewPage);
						
						
				reviewValues.add("\n" + "Category: " + category + ", " + "Product: " + productName + ", " 
						+ "Rating value: " + ratingValue + " out of " + maxValue + ", "  + "Published date: " + dateReview);
				}
						
			}
				return reviewValues;
		}
		
}
