package utils;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.time.Duration;

public class WebDriverUtils extends LoggerUtils{

    public static WebDriver setupDriver(String sBrowser) {
        WebDriver driver = null;

        log.debug("setupDriver(" + sBrowser + ")");
        switch (sBrowser.toLowerCase()) {
            case "chrome" : {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--window-size=1600,900");
                    driver = new ChromeDriver(options);

                break;
            }
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--window-size=1600,900");
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
//        driver.manage().window().maximize();

        return driver;
    }

    public static void quitDriver(WebDriver driver) {
        log.debug("quitDriver()");
        driver.quit();
    }

    public static boolean hasDriverQuit(WebDriver driver) {
        if(driver != null) {
            return ((RemoteWebDriver) driver).getSessionId() == null;
        } else {
            return true;
        }
    }
}
