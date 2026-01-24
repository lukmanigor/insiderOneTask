package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.*;

public abstract class BaseTest extends LoggerUtils {

    protected WebDriver setupDriver() {
        log.debug("setupDriver()");
        return WebDriverUtils.setupDriver();
    }

    protected void quitDriver(WebDriver driver) {
        log.debug("quitDriver()");
        WebDriverUtils.quitDriver(driver);
    }

    private void ifFailed(WebDriver driver, ITestResult testResult) {
        if(testResult.getStatus() == ITestResult.FAILURE) {
            if (PropertiesUtils.getTakeScreenshots()) {
                String sTestName = testResult.getTestClass().getName();
                ScreenshotUtils.takeScreenshot(driver, sTestName);
            }
        }
    }

    protected void tearDown(WebDriver driver, ITestResult testResult) {
        log.debug("tearDownDriver()");
        String sTestName = testResult.getTestClass().getName();
        try{
            ifFailed(driver, testResult);
        } catch (AssertionError | Exception e) {
            log.error("Error during taking screenshot for test '" + sTestName + "'. Message: " + e.getMessage());
        }
        quitDriver(driver);
    }
}
