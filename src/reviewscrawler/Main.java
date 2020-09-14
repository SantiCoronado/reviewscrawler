package reviewscrawler;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;



public class Main {
	
	
	public static void main(String[] args) throws IOException {
	
		
		//TESTS FOR TECHRADAR
		/*
		TechradarCrawler techradarcrawl = new TechradarCrawler();
		Set<String> techradarCategories = techradarcrawl.getCategoryLinks() ;
		Set<String> techradarSubcategories = techradarcrawl.getSubcategoryLinks();
		Set<String> techradarReviews = techradarcrawl.getReviewLinks();
		Set<String> techradarReviewValues = techradarcrawl.getReviewValues();
		
		System.out.println("Number of crawled categories: " + (techradarCategories.size()));
		System.out.println(techradarCategories);		
		System.out.println("Number of crawled subcategories: " + techradarSubcategories.size());
		System.out.println(techradarSubcategories);
		System.out.println("Number of crawled reviews: " + techradarReviews.size());	
		System.out.println(techradarReviewValues);
		*/
		
		
		
		//TESTS FOR AMAZON UK
		
		//TEST TO GIVE FINAL REVIEW VALUES
		
		AmazonUkCrawler amazonscrap = new AmazonUkCrawler();
		Set<String> amazonRating = amazonscrap.getReviewValues();
		System.out.println(amazonRating);
		
		
		/*
		// TEST TO CRAWL AMAZON CATEGORIES AND SUBCATEGORIES LINKS
		AmazonUkCrawler amazoncrawl = new AmazonUkCrawler();
		Set<String> amazonUkCategories = amazoncrawl.getCategoryLinks() ;
		System.out.println("Number of crawled categories: " + (amazonUkCategories.size()));
		System.out.println(amazonUkCategories);
		
		
		//AmazonUkCrawler amazoncrawl = new AmazonUkCrawler();
		Set<String> amazonUkSubcategories = amazoncrawl.getSubcategoryLinks(amazonUkCategories) ;
		System.out.println("Number of crawled subcategories: " + (amazonUkSubcategories.size()));
		System.out.println(amazonUkSubcategories);
		*/
		
		/*
		AmazonUkCrawler amazonscrap = new AmazonUkCrawler();
		//TEST 1: This URL will connect to around 64 "Prepaid phone cards" Amazon's products
		String urlConnection = "https://www.amazon.co.uk/s/ref=sr_nr_n_5?fst=as%3Aoff&rh=n%3A560798%2Cn%3A%21560800%2Cn%3A1340513031%2Cn%3A1340861031&bbn=1340513031&ie=UTF8&qid=1499270843&rnid=1340513031";
		
		//TEST 2: This URL will connect to around 238 "Answer Machines" Amazon's products
		String urlConnection2 = "https://www.amazon.co.uk/s/ref=lp_1340513031_nr_n_2?fst=as%3Aoff&rh=n%3A560798%2Cn%3A%21560800%2Cn%3A1340513031%2Cn%3A10395451&bbn=1340513031&ie=UTF8&qid=1499273812&rnid=1340513031";
		Set<String> reviewLinks = amazonscrap.getReviewLinks(urlConnection2);
		System.out.println("Number of crawled reviews: " + (reviewLinks.size()));
		System.out.println(reviewLinks);
		*/
		
		
		/*
		// TEST X: CALCULATE AVERAGE SCORE ONLY IN VERIFIED REVIEWS
		AmazonUkCrawler amazonscrap = new AmazonUkCrawler();
		Document amazonUkConnect = amazonscrap.connectTo("https://www.amazon.co.uk/Samsung-Galaxy-32GB-SIM-Free-Smartphone/dp/B01C5OIIF2/ref=sr_1_4?s=electronics&ie=UTF8&qid=1473649868&sr=1-4&keywords=samsung+galaxy+s7");
		String verifiedReviews = amazonscrap.getVerifiedLinkNewStruc(amazonUkConnect);
		List<String> verifiedResults = amazonscrap.getVerifiedReviewValues(verifiedReviews);
		String verifiedAvg = amazonscrap.getVerifiedAverage(verifiedResults);
		System.out.println(verifiedAvg);
		*/
		
		
		//TESTS FOR AMAZON US
		
		// TEST TO CRAWL AMAZON CATEGORIES AND SUBCATEGORIES LINKS
		/*
		AmazonUsCrawler amazoncrawl = new AmazonUsCrawler();
		Set<String> amazonUsCategories = amazoncrawl.getCategories() ;
		System.out.println("Number of crawled categories: " + (amazonUsCategories.size()));
		System.out.println(amazonUsCategories);
		
		
		//AmazonUsCrawler amazoncrawl = new AmazonUsCrawler();
		Set<String> amazonUsSubcategories = amazoncrawl.getSubcategoryLinks(amazonUsCategories) ;
		System.out.println("Number of crawled subcategories: " + (amazonUsSubcategories.size()));
		System.out.println(amazonUsSubcategories);
		*/
		
		/*
		AmazonUsCrawler amazoncrawl = new AmazonUsCrawler();
		String urlConnection = "https://www.amazon.com/s/ref=lp_6563140011_nr_i_6?srs=6563140011&fst=as%3Aoff&rh=i%3Aspecialty-aps%2Ci%3Alawngarden&ie=UTF8&qid=1499683378";
		Set<String> reviewLinks = amazoncrawl.getReviewLinks(urlConnection);
		System.out.println("Number of crawled reviews: " + (reviewLinks.size()));
		System.out.println(reviewLinks);
		*/
		
		/*
		// TEST X: CALCULATE AVERAGE SCORE ONLY IN VERIFIED REVIEWS
		
		AmazonUsCrawler amazonscrap = new AmazonUsCrawler();
		Document amazonUsConnect = amazonscrap.connectTo("https://www.amazon.com/Energup-ABP1801-Replacement-Cordless-Battery/dp/B01GY68C5I/ref=sr_1_7?s=home-automation&srs=6563140011&ie=UTF8&qid=1499687787&sr=1-7");
		String verifiedReviews = amazonscrap.getVerifiedLinkNewStruc(amazonUsConnect);
		List<String> verifiedResults = amazonscrap.getVerifiedReviewValues(verifiedReviews);
		String verifiedAvg = amazonscrap.getVerifiedAverage(verifiedResults);
		System.out.println(verifiedAvg);
		*/
		
		
		/*
		AmazonUsCrawler amazonscrap = new AmazonUsCrawler();
		Set<String> amazonRating = amazonscrap.getReviewValues();
		System.out.println(amazonRating);
		*/
		
		/*
		CurrencyConverter toGbp = new CurrencyConverter();
		String rateExchange = toGbp.convertUsdToGbp("$116.99");
		System.out.println(rateExchange);
		*/
	}
}