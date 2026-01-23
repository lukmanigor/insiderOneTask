package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class QAJobsPage extends BasePage {

    public static final String QA_JOBS_PAGE_URL = "https://insiderone.com/careers/quality-assurance/";

    // Locators
    @FindBy (xpath = "//h1[contains(@class, 'big-title')]")
    private WebElement qaJobsPageHeaderLocator;

    @FindBy (xpath = "//div[contains(@class, 'button-group')]//a[@href = 'https://insiderone.com/careers/open-positions/?department=qualityassurance']")
    private WebElement seeAllQAJobsButtonLocator;

    // Constructor
    public QAJobsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        log.trace("new QAJobsPage()");
    }

    public QAJobsPage open() {
        log.debug("open()");
        driver.get(QA_JOBS_PAGE_URL);
        return this;
    }

    public boolean isQAJobsPageLoaded(int iTimeout) {
        log.debug("isQAJobsPageLoaded()");
        waitForURLChange(QA_JOBS_PAGE_URL, iTimeout);
        return isWebElementDisplayed(qaJobsPageHeaderLocator);
    }

    public boolean isQAJobsPageLoaded() {
        log.debug("isQAJobsPageLoaded()");
        return isQAJobsPageLoaded(5);
    }

    public OpenPositionsPage clickSeeAllQAJobsButton() {
        log.debug("clickSeeAllQAJobsButton()");
        waitForElementToBeClickable(seeAllQAJobsButtonLocator, Time.TIME_SHORTER);
        clickWebElement(seeAllQAJobsButtonLocator);
        return new OpenPositionsPage(driver);
    }


}
