package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomePage extends BasePage {

    public static final String WELCOME_PAGE_URL = "https://insiderone.com/";

    // Locators
    @FindBy (id = "navigation")
    private WebElement welcomePageHeaderLocator;

    @FindBy (xpath= "//div[@class = 'header-top']")
    private WebElement welcomePageTopMenuLocator;

    @FindBy (xpath = "//div[@class = 'header-wrapper']")
    private WebElement welcomePageHeaderMenuLocator;

    @FindBy (xpath = "//section[@class = 'homepage-hero']")
    private WebElement welcomePageHeroCardLocator;

    @FindBy (xpath = "//div[contains(@class, 'homepage-hero-content')]")
    private WebElement welcomePageHeroContentLocator;

    @FindBy (xpath = "//section[contains(@class, 'homepage-social-proof')]")
    private WebElement welcomePageSocialProofLocator;

    @FindBy (xpath = "//footer[contains(@class, 'footer')]")
    private WebElement welcomePageFooterLocator;

    // Constructor
    public WelcomePage(org.openqa.selenium.WebDriver driver) {
        super(driver);
        this.driver = driver;
        log.trace("new WelcomePage()");
    }

    public WelcomePage open() {
        log.debug("open()");
        driver.get(WELCOME_PAGE_URL);
        return this;
    }

    public boolean isWelcomePageLoaded(int iTimeout) {
        log.debug("isWebElementDisplayed()");
        waitForURLChange(WELCOME_PAGE_URL, iTimeout);
        return isWebElementDisplayed(welcomePageHeaderLocator);
    }

    public boolean isWelcomePageLoaded() {
        log.debug("isWebElementDisplayed()");
        return isWelcomePageLoaded(5);
    }

    public boolean isTopHeaderMenuDisplayed() {
        log.debug("isTopHeaderMenuDisplayed()");
        return isWebElementDisplayed(welcomePageTopMenuLocator);
    }

    public boolean isHeaderMenuDisplayed() {
        log.debug("isHeaderMenuDisplayed()");
        return isWebElementDisplayed(welcomePageHeaderMenuLocator);
    }

    public boolean isHeroCardDisplayed() {
        log.debug("isHeroCardDisplayed()");
        return isWebElementDisplayed(welcomePageHeroCardLocator);
    }

    public boolean isHeroContentDisplayed() {
        log.debug("isHeroContentDisplayed()");
        return isWebElementDisplayed(welcomePageHeroContentLocator);
    }

    public boolean isLogoReelLogosDisplayed() {
        log.debug("isLogoReelLogosDisplayed()");
        return isWebElementDisplayed(welcomePageSocialProofLocator);
    }

    public boolean isFooterDisplayed() {
        log.debug("isFooterDisplayed()");
        return isWebElementDisplayed(welcomePageFooterLocator);
    }

}
