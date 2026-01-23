package utils;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;

import java.time.Duration;

public class WebDriverUtils extends LoggerUtils{

    public static WebDriver setupDriver() {
        WebDriver driver = null;

        String sBrowser = PropertiesUtils.getBrowser();
        String sDriversFolder = PropertiesUtils.getDriversFolder();

        String sPathDriverChrome = sDriversFolder + "chromedriver.exe";
        String sPathDriverFirefox = sDriversFolder + "geckodriver.exe";

        log.debug("setupDriver(" + sBrowser + ")");
        switch (sBrowser) {
            case "chrome" : {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--window-size=1600,900");

                    System.setProperty("webdriver.chrome.driver", sPathDriverChrome);
                    driver = new ChromeDriver(options);

                break;
            }
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--window-size=1600,900");
                    System.setProperty("webdriver.gecko.driver", sPathDriverFirefox);
                    driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            default: {
                Assert.fail("Cannot create driver! Browser '" + sBrowser + "' is not recognized!");
            }
        }

        // Default Timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.IMPLICIT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Time.SCRIPT_TIMEOUT));

        // Maximize Browser
        driver.manage().window().maximize();

        return driver;
    }

    public static void quitDriver(WebDriver driver) {
        log.debug("quitDriver()");
        driver.quit();
    }
}
