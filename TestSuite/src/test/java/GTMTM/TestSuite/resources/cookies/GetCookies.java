package GTMTM.TestSuite.resources.cookies;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetCookies {
	private final WebDriver driver;
	
	public GetCookies(ThreadLocal<WebDriver> driver2)
	{
		this.driver = driver2.get();
	}
	
    public static void main(String[] args) throws IOException, InterruptedException
    {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://dispatch-test.mtm-inc.net");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("seppa@mtm-inc.net");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Mar@1234");
        driver.findElement(By.xpath("//input[@value='Sign in']")).click();
        try {
        	driver.navigate().to("https://dispatch-test.mtm-inc.net");
        	Thread.sleep(5000);
			String cookieFileName = "dispatchCookies.json";
			writeCookiesToFile(cookieFileName, driver);
			driver.navigate().to("https://spiceportal-test.mtm-inc.net");
        	Thread.sleep(5000);
        	cookieFileName = "spicePortalCookies.json";
        	writeCookiesToFile(cookieFileName, driver);
		}
        finally {
			driver.quit();
        }
    }
    
    public static void writeCookiesToFile(String cookieFileName, WebDriver driver)
    {
    	Set<Cookie> cookies = driver.manage().getCookies();
		Gson gson = new Gson();
		String cookiesJson = gson.toJson(cookies);
    	try (FileWriter file = new FileWriter("src\\test\\java\\GTMTM\\TestSuite\\resources\\cookies\\" + cookieFileName)) {
			file.write(cookiesJson);
			System.out.println("Completed writing New Cookies into File - " + cookieFileName);
		} catch(IOException ex)
		{
			System.out.println("Error writing into File" + ex.getMessage());
		}
	}  
    
    public void getCookies(String cookieFileName)
    {
    	try
		{
			Gson gson = new Gson();
			Type cookieListType = new TypeToken<List<Cookie>>(){}.getType();
			List<Cookie> cookies = gson.fromJson(new FileReader("src\\test\\java\\GTMTM\\TestSuite\\resources\\cookies\\" + cookieFileName), cookieListType);
			for(Cookie cookie : cookies)
			{
				driver.manage().addCookie(cookie);
			}
			driver.navigate().refresh();
		}
		catch(IOException ex)
		{
			System.out.println("Unable to inject cookies into the browser" + ex.getMessage());
		}
    }
}
