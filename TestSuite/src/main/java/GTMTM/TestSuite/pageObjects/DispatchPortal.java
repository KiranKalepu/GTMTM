package GTMTM.TestSuite.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DispatchPortal {
     WebDriver driver;
     
     public DispatchPortal(WebDriver driver)
     {
          this.driver = driver;
          PageFactory.initElements(driver, this);
     }
     
     @FindBy(xpath = "//nav //a")
     private List<WebElement> tabsElementList;
     
     public List<WebElement> getTabsList()
     {
    	 return tabsElementList;
     }
     
     @FindBy(xpath = "//icon")
     private WebElement closeIcon;
     
     public WebElement getCloseIcon()
     {
    	 return closeIcon;
     }
     
     @FindBy(className = "page-title")
     private WebElement pageTitle;
     
     public WebElement getPageTitle()
     {
    	 return pageTitle;
     }
     
     @FindBy(xpath = "//div //p")
     private WebElement getUserName;
     
     public WebElement getUserName()
     {
    	 return getUserName;
     }
     
     @FindBy(xpath = "//button[text()='Sign Out']")
     private WebElement getSingOutButton;
     
     public void clickOnSignOut()
     {
    	 getSingOutButton.click();
     }
     
     
}
