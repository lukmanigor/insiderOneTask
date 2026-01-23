package tests;

import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;
import utils.WebDriverUtils;

public abstract class BaseTest extends LoggerUtils {

    protected WebDriver setupDriver() {
        log.debug("setupoDriver()");
        return WebDriverUtils.setupDriver();
    }

    protected void quitDriver(WebDriver driver) {
        log.debug("quitDriver()");
        WebDriverUtils.quitDriver(driver);
    }

    protected void tearDown(WebDriver driver) {
        log.debug("tearDownDriver()");
        quitDriver(driver);
    }
}
