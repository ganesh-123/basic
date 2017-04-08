package Se_package;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Shoes 
{
	
public static WebDriver driver;
	
	public static void main(String[] args) throws Exception 
	
	{
		System.setProperty("webdriver.chrome.driver", "D://Selenium jars//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rb-shoe-store.herokuapp.com/definition");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		if(driver.findElement(By.id("remind_email_form")).isDisplayed())
			System.out.println("Submit Email address section for reminder about shoes is displayed for user on definition page(https://rb-shoe-store.herokuapp.com/definition)"+"\n");
		List<WebElement> months = driver.findElements(By.xpath("//ul//a"));
		
		String tem[]=new String[months.size()];
		int i=0;
		for(WebElement w: months)
		{
			if(i<months.size())
			tem[i]=w.getText();	
			i++;
		}
		
		for(int j=0;j<tem.length-1;j++)
		{
			if(tem[j].equals("All Shoes"))
			{
				continue;
			}
			WebElement frt=driver.findElement(By.linkText(tem[j]));
			frt.click();
			verifyBrands(tem[j]);
			System.out.println();
			emailNotify(tem[j]);
			System.out.println("\n\n");
		}
		
		
		
	}
	
	static void verifyBrands(String monthName)
	{
		blurbVerify(monthName);
		priceVerify(monthName);
		imageVerify(monthName);
	}
	
	static void blurbVerify(String month)
	{
List<WebElement> er=driver.findElements(By.xpath("//ul[@id='shoe_list']/li"));
		
		if(er.size()==0)
		{
			System.out.println("No shoe blurb displayed for the month"  + month);
		}
		else
		{
			System.out.println("Shoe blurb is displayed for the month" + month + "and number of blurbs are" + er.size());
		}
	}
	
	static void priceVerify(String month)
	{
		List<WebElement> er=driver.findElements(By.xpath("//ul[@id='shoe_list']/li"));
		while(er.size()!=0)
		{
			for(int i=1;i<=er.size();i++)
			{
			String priceXpathDynamic=String.format("//ul[@id='shoe_list']/li[%s]/div/table/tbody/tr/td[contains(@class,'shoe_price')]",i);
			String price=driver.findElement(By.xpath(priceXpathDynamic)).getText().trim();
			String shoeBrandDynamic=String.format("//ul[@id='shoe_list']/li[%s]/div/table/tbody/tr/td[contains(@class,'shoe_brand')]/a",i);
			String shoeBrand=driver.findElement(By.xpath(shoeBrandDynamic)).getText().trim();
			if(price!=null)
			{
				System.out.println("The Price for the Shoe Brand" + shoeBrand + "is" + price);
			}
			else
			{
				System.out.println("The Price for the Shoe Brand" + shoeBrand + "is " + null);
			}
		}
			break;
		}
	}
	static void imageVerify(String month)
	{
		List<WebElement> er=driver.findElements(By.xpath("//ul[@id='shoe_list']/li"));
		while(er.size()!=0)
		{
			for(int i=1;i<=er.size();i++)
			{
				String shoeBrandDynamic=String.format("//ul[@id='shoe_list']/li[%s]/div/table/tbody/tr/td[contains(@class,'shoe_brand')]/a",i);
				String shoeimageDynamic = String.format("//ul[@id='shoe_list']/li[%s]/div/table/tbody/tr/td[contains(@class,'shoe_image')]/img", i);
				String shoeBrand=driver.findElement(By.xpath(shoeBrandDynamic)).getText().trim();
				String shoeimage = driver.findElement(By.xpath(shoeimageDynamic)).getAttribute("src");
				if(shoeimage.contains(".jpg"))
				{
					System.out.println("Shoe image is displayed for the brand" + shoeBrand +" for the month " + month);
				}
				else
					System.out.println("Shoe image is missing for the brand" + shoeBrand + " for the month " + month);
				
			}
			break;
			}
		
		
		
	}

	static void emailNotify(String monthName)
	{
		if(driver.findElement(By.id("remind_email_form")).isDisplayed())
		{
			System.out.println("Submit Email address section for reminder is display for the month" + monthName);
			driver.findElement(By.id("remind_email_input")).sendKeys("test@gmail.com");
			driver.findElement(By.id("remind_email_submit")).click();
			String msg = driver.findElement(By.xpath("//div[@class='col-md-4 col-md-offset-4']/div/div")).getText();
			System.out.println("If valid email address is provided user will be able to see successful message as" + msg);
	}
		else
			System.out.println("Submit Email address section for reminder is missing for the month" + monthName);
	}
	
}