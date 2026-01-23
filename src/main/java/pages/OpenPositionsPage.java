package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OpenPositionsPage extends BasePage {

    public static final String OPEN_POSITIONS_PAGE_URL = "https://insiderone.com/careers/open-positions";
    public static final String LEVER_CAREERS_URL = "https://jobs.lever.co/insiderone";

    private static final Map<String, String> LOCATION_MAP = Map.of(
            "Istanbul, Turkiye", "istanbulturkiye"
    );

    private static final Map<String, String> DEPARTMENT_MAP = Map.of(
            "Quality Assurance", "qualityassurance"
    );

    private static final String POSITION_TITLE_QA = "Quality Assurance";
    private static final String POSITION_DEPARTMENT_QA = "Quality Assurance";
    private static final String POSITION_LOCATION_ISTANBUL = "Istanbul, Turkiye";

    // Locators
    @FindBy (xpath = "//div[@class = 'career-top']")
    private WebElement openPositionsPageHeaderLocator;

    @FindBy (xpath = "//p[contains(@class, 'no-job-result')]")
    private WebElement noJobsFoundLocator;

    @FindBy (id = "filter-by-location")
    private WebElement locationFilterDropdownLocator;

    @FindBy (id = "filter-by-department")
    private WebElement departmentFilterDropdownLocator;

    @FindAll (
            @FindBy(xpath = "//option[contains(@class, 'job-location')]"))
    private List<WebElement> locationOptionsLocator;

    @FindAll (
            @FindBy(xpath = "//option[contains(@class, 'job-team')]"))
    private List<WebElement> departmentOptionsLocator;

    @FindBy (id = "jobs-list")
    private WebElement positionsListLocator;

    @FindAll (
            @FindBy(xpath = "//div[contains(@class, 'position-list-item-wrapper')]"))
    private List<WebElement> positionItemsLocator;

    // Constructor
    public OpenPositionsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        log.trace("new OpenPositionsPage()");
        handleCookies();
    }

    public boolean isOpenPositionsPageLoaded(int iTimeout) {
        log.debug("isOpenPositionsPageLoaded()");
        waitForURLChange(OPEN_POSITIONS_PAGE_URL, 5);
        waitUntilGone(By.xpath("//p[contains(@class, 'no-job-result')]"), iTimeout);
        return isWebElementDisplayed(openPositionsPageHeaderLocator);
    }

    public boolean isOpenPositionsPageLoaded() {
        log.debug("isOpenPositionsPageLoaded()");
        return isOpenPositionsPageLoaded(Time.TIME_LONGEST);
    }

    public boolean isLocationFilterDropdownDisplayed() {
        log.debug("isLocationFilterDropdownDisplayed()");
        return isWebElementDisplayed(locationFilterDropdownLocator);
    }

    public void clickLocationFilterDropdown() {
        log.debug("clickLocationFilterDropdown()");
        Assert.assertTrue(isLocationFilterDropdownDisplayed(), "Location filter dropdown is not displayed!");
        clickWebElement(locationFilterDropdownLocator);
    }

    public void clickDepartmentFilterDropdown() {
        log.debug("clickDepartmentFilterDropdown()");
        Assert.assertTrue(isWebElementDisplayed(departmentFilterDropdownLocator), "Department filter dropdown is not displayed!");
        clickWebElement(departmentFilterDropdownLocator);
    }

    public void selectLocation(String sLocation){
        log.debug("selectLocation(" + sLocation + ")");
        clickLocationFilterDropdown();
        if(sLocation != null ) {
            String sLocationValue = LOCATION_MAP.get(sLocation);
            for (WebElement option : locationOptionsLocator) {
                String optionValue = option.getAttribute("class");
                if (optionValue != null && optionValue.contains(sLocationValue)) {
                    clickWebElement(option);
                    // Hardcoded wait to allow filters to be applied
                    sleepSeconds(Time.TIME_SHORTER);
                    return;
                }
            }
        }
    }

    public void selectDepartment(String sDepartment){
        log.debug("selectDepartment(" + sDepartment + ")");
        clickDepartmentFilterDropdown();
        if(sDepartment != null) {
            String sDepartmentValue = DEPARTMENT_MAP.get(sDepartment);
            for (WebElement option : departmentOptionsLocator) {
                String optionValue = option.getAttribute("class");
                if (optionValue != null && optionValue.contains(sDepartmentValue)) {
                    clickWebElement(option);
                    // Hardcoded wait to allow filters to be applied
                    sleepSeconds(Time.TIME_SHORTER);
                    return;
                }
            }
        }
    }

    public String getSelectedLocation(){
        log.debug("getSelectedLocation()");
        String selectedLocation = locationFilterDropdownLocator.getAttribute("value");
        log.info("Selected location: " + selectedLocation);
        return selectedLocation;
    }

    public String getSelectedDepartment(){
        log.debug("getSelectedDepartment()");
        String selectedDepartment = departmentFilterDropdownLocator.getAttribute("value");
        log.info("Selected department: " + selectedDepartment);
        return selectedDepartment;
    }

    public boolean isJobsListDisplayed() {
        log.debug("isJobsListDisplayed()");
        return isWebElementDisplayed(positionsListLocator);
    }

    public void verifyQAPositions() {
        log.debug("verifyQAPositions");
        for (WebElement positionItem : positionItemsLocator) {
            WebElement positionTitle = positionItem.findElement(By.xpath("//p[contains(@class, 'position-title')]"));
            String sPositionTitleText = positionTitle.getText();

            WebElement positionDepartment = positionItem.findElement(By.xpath("//span[contains(@class, 'position-department')]"));
            String sPositionDepartmentText = positionDepartment.getText();

            WebElement positionLocation = positionItem.findElement(By.xpath("//div[contains(@class, 'position-location')]"));
            String sPositionLocationText = positionLocation.getText();

            Assert.assertTrue(sPositionTitleText.contains(POSITION_TITLE_QA), "Position title does not contain 'Quality Assurance': " + sPositionTitleText);
            Assert.assertEquals(sPositionDepartmentText, POSITION_DEPARTMENT_QA, "Position department is not 'Quality Assurance': " + sPositionDepartmentText);
            Assert.assertEquals(sPositionLocationText, POSITION_LOCATION_ISTANBUL, "Position location is not 'Istanbul, Turkiye': " + sPositionLocationText);

        }
    }

    public void clickViewRole(String sPositionName) {
        log.debug("clickViewRole(" + sPositionName + ")");
        for (WebElement positionItem : positionItemsLocator) {
            WebElement positionTitle = positionItem.findElement(By.xpath(".//p[contains(@class, 'position-title')]"));
            String sPositionTitleText = positionTitle.getText();
            if (sPositionTitleText.contains(sPositionName)) {
                WebElement viewRoleButton = positionItem.findElement(By.xpath(".//a[contains(@class, 'btn-navy')]"));
                clickWebElement(viewRoleButton);
                return;
            }
        }
        Assert.fail("Position with name '" + sPositionName + "' not found.");
    }

    public void clickViewRoleAndVerifyLeverTabOpened(String sPositionName) {
        log.debug("clickViewRoleAndVerifyLeverTabOpened()");
        String parentTab = driver.getWindowHandle();
        int initialTabCount = driver.getWindowHandles().size();

        clickViewRole(sPositionName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(driver -> driver.getWindowHandles().size() > initialTabCount);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parentTab)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        String sCurrentUrl = driver.getCurrentUrl();
        Assert.assertTrue(sCurrentUrl.contains(LEVER_CAREERS_URL), "Expected Lever page, but got: " + sCurrentUrl);

    }

}
