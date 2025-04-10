package GTMTM.TestSuite.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import GTMTM.TestSuite.resources.cookies.GetCookies;

public class BasePage {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void initializeDriver(@Optional("chrome") String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                webDriver = new ChromeDriver();
                break;
            case "edge":
                webDriver = new EdgeDriver();
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(webDriver);
        logger.info("[" + browser + "] Browser launched for thread: " + Thread.currentThread().toString());
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            logger.info("Browser closed for thread: " + Thread.currentThread().toString());
            driver.remove();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public Logger getLogger() {
        return logger;
    }

    public void launchUrlWithCookies(String url, String cookieFileName) throws Exception {
        getDriver().get(url);
        getDriver().manage().deleteAllCookies();
        GetCookies cookies = new GetCookies(driver);
        cookies.getCookies(cookieFileName);
    }
}
