package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils extends LoggerUtils{

    private static final String sScreenshotPath = System.getProperty("user.dir") + PropertiesUtils.getScreenShotsFolder();

    private static String createScreenshotPath(String sScreenshotName) {
        return sScreenshotPath + sScreenshotName + ".png";
    }

    public static void takeScreenshot(WebDriver driver, String sTestName) {
        log.trace("takeScreenshot(" + sTestName + ")");
        String sPathToFile = createScreenshotPath(sTestName);
        if(WebDriverUtils.hasDriverQuit(driver)) {
            log.warn("Cannot take screenshot. WebDriver has already been closed.");
            return;
        }
        TakesScreenshot screenShot = ((TakesScreenshot) driver);
        File srcFile = screenShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(sPathToFile);
        try {
            FileUtils.copyFile(srcFile, destFile);
            log.info("Screenshot for test '" + sTestName + "' saved to: " + sPathToFile);
        } catch (IOException e) {
            log.warn("Screenshot for test '" + sTestName + "' cannot be saved. Message: " + e.getMessage());
        }
    }
}
