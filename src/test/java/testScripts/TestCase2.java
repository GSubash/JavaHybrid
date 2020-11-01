package testScripts;

import commonMethods.CommonClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pageObjects.CarePage;
import pageObjects.LogOnPage;
import pageObjects.PatientsPage;

import java.io.IOException;

public class TestCase2 {

    WebDriver driver;
    @BeforeMethod
    public void beforeTest() throws IOException
    {
        CommonClass obj = new CommonClass(driver);
        driver = obj.startbrowser("chrome");
    }

/**-----------------------------------------------------------------------------------------------------------------
 * TestCase 2 :
 * Verify Page Opens scroll to the bottom
 ------------------------------------------------------------------------------------------------------------------*/
    @Test(enabled = false)
    public void verifyPageScroll() throws IOException, InterruptedException
    {
        PageFactory.initElements(driver, LogOnPage.class);
        LogOnPage.Login(driver);
        Reporter.log("Step 1: Application is logged in successfully");

        PageFactory.initElements(driver, CarePage.class);
        CarePage.navigateToCare(driver);
        Reporter.log("Step 2: Navigated to Care Page successfully");

        CarePage.selectBranch(driver);
        Reporter.log("Step 3: Requested Branch selected Successfully");

        PageFactory.initElements(driver, PatientsPage.class);
        PatientsPage.selectPatientAndVerify(driver);
        Reporter.log("Step 4: Patient Name is verified Successfully");

        PatientsPage.scrollPatientsPage(driver);
        Reporter.log("Step 5: Patient Page Scroll is verified Successfully");
    }

    @AfterMethod
    public void afterTest() throws IOException, InterruptedException
    {
        CommonClass.closeAllBrowsers(driver);
    }

}
