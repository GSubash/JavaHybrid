package testScripts;

import commonMethods.CommonClass;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;
import pageObjects.CarePage;
import pageObjects.LogOnPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pageObjects.PatientsPage;

import java.io.IOException;
import java.util.List;

public class TestCase1 {

    WebDriver driver;
    @BeforeMethod
    public void beforeTest() throws IOException
    {
        CommonClass obj = new CommonClass(driver);
        driver = obj.startbrowser("chrome");
    }
/**-----------------------------------------------------------------------------------------------------------------
 * TestCase 1 :
 * Access the patient record by clicking on the patient name for any episode type.
 * Verify Patient Clicked is the same Patient Record that opens.
 ------------------------------------------------------------------------------------------------------------------*/
    @Test
    public void verifyPatientRecord() throws IOException, InterruptedException
    {
        PageFactory.initElements(driver, LogOnPage.class);
        LogOnPage.Login(driver);
        Reporter.log("Step 1: Application is logged in successfully");

        PageFactory.initElements(driver, CarePage.class);
        CarePage.navigateToCare(driver);
        Reporter.log("Step 2: Navigated to Care Page successfully");

        CarePage.selectBranch(driver);
        Reporter.log("Step 3: Branch is selected successfully");

        PageFactory.initElements(driver, PatientsPage.class);
        PatientsPage.selectPatientAndVerify(driver);
        Reporter.log("Step 4: Patient Name is verified Successfully");
    }
    @AfterMethod
    public void afterTest() throws IOException, InterruptedException
    {
        CommonClass.closeAllBrowsers(driver);
    }
}
