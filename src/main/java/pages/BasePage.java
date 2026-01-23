package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtils;

import java.time.Duration;

public abstract class BasePage extends LoggerUtils {

    // Locators
    @FindBy (id = "cookie-law-info-bar")
    protected WebElement cookieConsentBannerLocator;

    @FindBy (id = "wt-cli-accept-all-btn")
    protected WebElement acceptCookiesButtonLocator;

    @FindBy (xpath = "//div[contains(@id, 'wrap-close-button')]")
    protected WebElement closeBannerButtonLocator;

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebElement getWebElement(By locator) {
        log.trace("getWebElement(" + locator + ")");
        return driver.findElement(locator);
    }

    protected boolean waitForURLChange(String sURL, int timeout){
        log.trace("waitForURLChange(" + sURL + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlContains(sURL));
    }

    protected boolean waitForExactURL(String sURL, int timeout){
        log.trace("waitForExactURL(" + sURL + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlToBe(sURL));
    }

    protected boolean waitUntilPageIsReady(int timeout) {
        log.trace("waitUntilPageIsReady(" + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    protected WebElement getWebElement(By locator, int timeout) {
        log.trace("getWebElement(" + locator + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement getWebElement(By locator, int timeout, int pollingInterval) {
        log.trace("getWebElement(" + locator + ", " + timeout + ", " + pollingInterval + ")");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingInterval))
                .ignoring(NoSuchElementException.class);
        return wait.until(driver1 -> driver1.findElement(locator));
    }

    protected boolean isWebElementDisplayed(By locator, int timeout){
        log.trace("isWebElementDisplayed(" + locator + ", " + timeout + ")");
        try {
            WebElement element = getWebElement(locator, timeout);
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isWebElementDisplayed(WebElement element){
        log.trace("isWebElementDisplayed(" + element + ")");
        try {
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected WebElement waitForElementToBeClickable(By locator, int timeout) {
        log.trace("waitForElementToBeClickable(" + locator + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementToBeClickable(WebElement element, int timeout) {
        log.trace("waitForElementToBeClickable(" + element + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForElementToBeVisible(By locator, int timeout) {
        log.trace("waitForElementToBeVisible(" + locator + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementToBeVisible(WebElement element, int timeout) {
        log.trace("waitForElementToBeVisible(" + element + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOf(element));
        return null;
    }

    protected boolean waitForElementToBeInvisible(By locator, int timeout) {
        log.trace("waitForElementToBeInvisible(" + locator + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected boolean waitForElementToBeInvisible(WebElement element, int timeout) {
        log.trace("waitForElementToBeInvisible(" + element + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void clickWebElement(WebElement element) {
        log.trace("clickWebElement(" + element + ")");
        element.click();
    }

    public void handleCookies(){
        log.trace("handleCookies()");
        if(isWebElementDisplayed(cookieConsentBannerLocator)){
            log.info("Cookie consent banner is displayed. Accepting cookies...");
            waitForElementToBeClickable(acceptCookiesButtonLocator, 5);
            clickWebElement(acceptCookiesButtonLocator);
        } else {
            log.info("Cookie consent banner is not displayed, proceeding with test.");
        }
    }

    public void handleBanner(){
        log.trace("handleBanner()");
        if(isWebElementDisplayed(closeBannerButtonLocator)){
            log.info("Close banner button is displayed. Closing banner...");
            waitForElementToBeClickable(closeBannerButtonLocator, 5);
            clickWebElement(closeBannerButtonLocator);
        } else {
            log.info("Close banner button is not displayed, proceeding with test.");
        }

    }

    protected void waitUntilGone(By locator, int iTime) {
        new WebDriverWait(driver, Duration.ofSeconds(iTime))
                .until(ExpectedConditions.numberOfElementsToBe(locator, 0));
    }

    protected void sleepSeconds(int iSeconds) {
        log.trace("sleepSeconds(" + iSeconds + ")");
        try {
            Thread.sleep(Duration.ofSeconds(iSeconds).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Sleep was interrupted", e);
        }
    }

}
