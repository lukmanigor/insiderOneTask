package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.OpenPositionsPage;
import pages.QAJobsPage;

public class FilterQAJobs extends BaseTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUpTest() {
        log.info("Starting test: FilterQAJobs");
        driver = setupDriver();
    }

    @Test
    public void testFilterQAJobs() {

        String sExpectedLocation = "Istanbul, Turkiye";
        String sExpectedDepartment = "Quality Assurance";
        String sPositionTitle = "Software Quality Assurance Engineer (Remote)";

         log.info("Test filtering QA jobs");
         QAJobsPage qaJobsPage = new QAJobsPage(driver).open();
         Assert.assertTrue(qaJobsPage.isQAJobsPageLoaded(), "QA Jobs page is not loaded!");

         log.info("Click 'See All QA Jobs' button and verify Open Positions page is loaded");
         OpenPositionsPage openPositionsPage = qaJobsPage.clickSeeAllQAJobsButton();
         Assert.assertTrue(openPositionsPage.isOpenPositionsPageLoaded(), "Open Positions page is not loaded!");

         log.debug("Click Location filter dropdown and select Istanbul, Turkiye");
         openPositionsPage.selectLocation("Istanbul, Turkiye");
         openPositionsPage.selectDepartment("Quality Assurance");

         log.debug("Verify that Location filter is set to Istanbul, Turkiye");
         String sSelectedLocation = openPositionsPage.getSelectedLocation();
         Assert.assertEquals(sSelectedLocation, sExpectedLocation, "Location filter is not set to Istanbul, Turkiye!");

         log.debug("Verify that Department filter is set to Quality Assurance");
         String sSelectedDepartment = openPositionsPage.getSelectedDepartment();
         Assert.assertEquals(sSelectedDepartment, sExpectedDepartment, "Department filter is not set to Quality Assurance!");

         log.debug("Verify jobs list is displayed");
         Assert.assertTrue(openPositionsPage.isJobsListDisplayed(), "Jobs list is not displayed!");

         log.debug("Verify listed jobs are QA jobs in Istanbul, Turkiye");
         openPositionsPage.verifyQAPositions();

         log.debug("Click on 'Software Quality Assurance Engineer' View Role button and verify user is redirected to jobs.lever page");
         openPositionsPage.clickViewRoleAndVerifyLeverTabOpened(sPositionTitle);

    }
    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.info("Ending test: FilterQAJobs");
        tearDown(driver, testResult);
    }
}
