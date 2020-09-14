package reviewscrawler;

import java.io.IOException;
import java.text.DecimalFormat;
import org.jsoup.nodes.Document;

public class CurrencyConverter
{
	
	public static String convertUsdToGbp(String price) throws IOException
	{
		String convertUsdToGbp = new String ("https://www.google.co.uk/finance/converter?a=1&from=USD&to=GBP&meta=ei%3DWavMV9iYCtaRUNj1voAG");
		Document currencyConverter = ReviewCrawler.connectTo(convertUsdToGbp);
		
		String rate = new String();
		String priceFigStr = new String();
		String ratePre = currencyConverter.select("div[id=currency_converter_result]").text();
		rate = ratePre.substring(ratePre.indexOf("=")+2, ratePre.indexOf("G")-1);
		
		double rateFigure = Double.parseDouble(rate);
		
		priceFigStr = price.substring(1);
		double priceFigure = Double.parseDouble(priceFigStr);
		
		double conversion = priceFigure * rateFigure;
		
		DecimalFormat df = new DecimalFormat("#.##");
		String conversionResult = "£" + df.format(conversion);

	return conversionResult;
	
	
	}
	
}
