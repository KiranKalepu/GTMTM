package GTMTM.TestSuite.base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import GTMTM.TestSuite.resources.cookies.GetGTMTMCookies;


public class GTMTMBasePage {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void initializeDriver(@Optional("edge") String browser) {
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
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        logger.info("[" + browser + "] Browser launched for thread: " + Thread.currentThread().toString());
    }

    @AfterClass(alwaysRun = true)
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
        GetGTMTMCookies cookies = new GetGTMTMCookies(driver);
        cookies.getCookies(cookieFileName);
    }
}
