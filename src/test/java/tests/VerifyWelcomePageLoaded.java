package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.WelcomePage;

public class VerifyWelcomePageLoaded extends BaseTest {

    @Test
    public void testVerifyWelcomePageLoaded() {

        WebDriver driver = setupDriver();

        try {

            log.info("Navigating to Welcome Page and verify it is loaded");
            WelcomePage welcomePage = new WelcomePage(driver).open();

            // Verify Welcome Page is loaded
            Assert.assertTrue(welcomePage.isWelcomePageLoaded());

            // Verify main elements are displayed
            Assert.assertTrue(welcomePage.isHeaderMenuDisplayed(), "Header Menu is not displayed!");
            Assert.assertTrue(welcomePage.isTopHeaderMenuDisplayed(), "Top Header Menu is not displayed!");
            Assert.assertTrue(welcomePage.isHeroCardDisplayed(), "Hero Card is not displayed!");
            Assert.assertTrue(welcomePage.isHeroContentDisplayed(), "Hero Content is not displayed!");
            Assert.assertTrue(welcomePage.isLogoReelLogosDisplayed(), "Logo Reel Logos are not displayed!");
            Assert.assertTrue(welcomePage.isFooterDisplayed(), "Footer is not displayed!");

        } finally {
            tearDown(driver);
        }
    }
}
