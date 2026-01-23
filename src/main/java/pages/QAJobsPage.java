package pages;

import org.openqa.selenium.WebDriver;

public class QAJobsPage extends BasePage {

    public static final String QA_JOBS_PAGE = "https://insiderone.com/careers/quality-assurance/";

    public QAJobsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        log.trace("new QAJobsPage()");
    }


}
