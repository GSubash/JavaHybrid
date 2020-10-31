package StepDefinitions;

import commonMethods.CommonClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import pageObjects.CarePage;
import pageObjects.LogOnPage;
import pageObjects.PatientsPage;

import java.io.IOException;

public class LoginStepdef {
    WebDriver driver;

    @Given("I open the url")
    public void iOpenTheUrl() throws IOException {

        CommonClass obj = new CommonClass(driver);
        driver = obj.startbrowser("chrome");
    }

    @And("I Login to the application")
    public void iLoginToTheApplication() throws IOException, InterruptedException {
        PageFactory.initElements(driver, LogOnPage.class);
        LogOnPage.Login(driver);
        Reporter.log("Step 1: Application is logged in successfully");
    }

    @When("I Navigate to Care page")
    public void iNavigateToCarePage() throws IOException, InterruptedException {
        PageFactory.initElements(driver, CarePage.class);
        CarePage.navigateToCare(driver);
        Reporter.log("Step 2: Navigated to Care Page successfully");
    }

    @And("I Select a branch")
    public void iSelectABranch() {
        CarePage.selectBranch(driver);
        Reporter.log("Step 3: Branch is selected successfully");
    }


    @Then("I Verify the patient name")
    public void iVerifyThePatientName() {
        PageFactory.initElements(driver, PatientsPage.class);
        PatientsPage.selectPatientAndVerify(driver);
        Reporter.log("Step 4: Patient Name is verified Successfully");
    }
}
