package automationFramework;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestCases {
	
	public static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		   driver = new FirefoxDriver();  		   	        
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		   driver.close();  
		   driver.quit();  		
	}

	// tc_03_005a
	// ARS.com shipping test
	public String tc_03_005a(){
		
		String url1 = "http://www.americanretailsupply.com/64931/173737/Garvey-1-Line-Price-Guns/Garvey-22-1-Line-Labelers.html";
		String zip1 = "98032";
				
		// ARS.com
		driver.get(url1);
		try{Thread.sleep(2000);}catch(Exception e){}
		
		driver.findElement(ById.id("ctl00_Main_275705")).click();
		try{Thread.sleep(2000);}catch(Exception e){}
		
		driver.findElement(ById.id("ctl00_Main_ZipCodeTextBox")).sendKeys(zip1);
		driver.findElement(ById.id("ctl00_Main_CalculateShippingEstimate")).click();
		try{Thread.sleep(8000);}catch(Exception e){}
		
		// xPath1: the xPath of Estimated Shipping cost in ARS.com
		String xPath1 = "//div[@id='ctl00_Main_BasketTotalsUpdatePanel']//div[@class='e6cart-rowbs'][3]//div[@class='e6cart-inputbs']";				
		String ship1 = driver.findElement(ByXPath.xpath(xPath1)).getText();
		try{Thread.sleep(2000);}catch(Exception e){}
						
		// empty the cart
		driver.findElement(ByLinkText.linkText("Remove")).click();
		try{Thread.sleep(2000);}catch(Exception e){}
		
		return ship1;
	}
	
	// tc_03_005b
	// ARS.net shipping test
	public String tc_03_005b(){				
		driver.get("http://americanretailsupply.net/labeler-garvey-22-6-1306.html");
		try{Thread.sleep(2000);}catch(Exception e){}
		
		driver.findElement(ById.id("product-addtocart-button")).click();
		try{Thread.sleep(2000);}catch(Exception e){}
		
		driver.get("http://americanretailsupply.net/checkout/cart/");
		try{Thread.sleep(2000);}catch(Exception e){}
		
		driver.findElement(ByXPath.xpath("//div[@id='block-shipping']//strong[@id='block-shipping-heading']")).click();
		try{Thread.sleep(2000);}catch(Exception e){}
		
		String xPathZip2 = "//form[@id='shipping-zip-form']//div[@class='field'][4]//input[@class='input-text']";
		driver.findElement(ByXPath.xpath(xPathZip2)).sendKeys("98032");;
		try{Thread.sleep(16000);}catch(Exception e){}
		
		String xPathGround2 = "//div[@class='field choice item'][1]//input[@value='ups_GND']";
		driver.findElement(ByXPath.xpath(xPathGround2)).click();
		try{Thread.sleep(8000);}catch(Exception e){}
		
		String xPathShipping2 = "//div[@id='cart-totals']//tr[@class='totals shipping excl']//td[@class='amount']//span";
		String observedShipping2 = driver.findElement(ByXPath.xpath(xPathShipping2)).getText();
				
		// empty the cart
		driver.findElement(ByXPath.xpath("//button[@id='empty_cart_button']")).click();
		try{Thread.sleep(8000);}catch(Exception e){}		
		
		return observedShipping2;
	}
	
	// tc_03_005
	// Verify that the estimated shipping cost 
	// between ARS.com and ARS.net are consistent	
	@Test
	public void tc_03_005(){
		String expectedShipping1 = tc_03_005a();
		String observedShipping2 = tc_03_005b();
		
		assertEquals(expectedShipping1, observedShipping2);
	}
  
}