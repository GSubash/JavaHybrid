package testScripts;

import commonMethods.CommonClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pageObjects.CarePage;
import pageObjects.LogOnPage;
import pageObjects.ReportsPage;

import java.io.IOException;

public class TestCase4 {

    WebDriver driver;
    @BeforeMethod
    public void beforeTest() throws IOException
    {
        CommonClass obj = new CommonClass(driver);
        driver = obj.startbrowser("chrome");
    }

/**-----------------------------------------------------------------------------------------------------------------
 * TestCase 4 :
 * Click and download the Patient Record View Report.
 * Ensure updated Patient record views show in the report.
 ------------------------------------------------------------------------------------------------------------------*/
    @Test(enabled = true)
    public void verifyUpdatedPatientRecord() throws IOException, InterruptedException
    {
        PageFactory.initElements(driver, LogOnPage.class);
        LogOnPage.Login(driver);
        Reporter.log("Step 1: Application is logged in successfully");

        PageFactory.initElements(driver, CarePage.class);
        CarePage.navigateToCare(driver);
        Reporter.log("Step 2: Navigated to Care Page successfully");

        PageFactory.initElements(driver, ReportsPage.class);
        ReportsPage.clickCareReports(driver);
        Reporter.log("Step 3: Care Reports page is selected Successfully");

        ReportsPage.downloadReport(driver,"Patient");
        Reporter.log("Step 4: Report is downloaded successfully");

        boolean status = ReportsPage.verifyPatientRecord();
        Assert.assertTrue(status,"Updated Record");
        Reporter.log("Step 5: Patient Record is verified for recent visits");
    }

    @AfterMethod
    public void afterTest() throws IOException, InterruptedException
    {
        CommonClass.closeAllBrowsers(driver);
    }

}
