package GTMTM.TestSuite.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MicrosoftLogin {
     WebDriver driver;
     
     public MicrosoftLogin(WebDriver driver)
     {
          this.driver = driver;
          PageFactory.initElements(driver, this);
     }
}
