package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PatientsPage {
    WebDriver driver;


    @FindBy(xpath = "//*[@id='summaryPanel1']//table//tr[1]/td[2]/a")
    public static WebElement lnkPatientName;

    @FindBy(xpath = "//table/tbody/tr/td[1]/div[2]")
    public static WebElement lblPatientName;

    @FindBy(xpath = "//div[2]/div/div/div[8]/div[1]")
    public static WebElement lblEpisodeHistory;

    @FindBy(xpath = "//div[2]/div/div/div[3]/div")
    public static WebElement lblNoRecommendation;

    @FindBy(xpath = "//*[@id='rec-hist-tbl']/tbody/tr[3]/td[1]/button")
    public static WebElement lblRecommendationRecord;

    @FindBy(xpath = "//div[2]/div/div/div[7]/div[1]")
    public static WebElement lblRecommendationTab;

    public PatientsPage(WebDriver driver)
    {
        this.driver = driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will select the patient record and verify selected patient is displayed
 * @param driver
------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver selectPatientAndVerify(WebDriver driver)
    {
        String patientName = lnkPatientName.getText();
        try{
            lnkPatientName.click();

            for(String handle : driver.getWindowHandles())
            {
                driver.switchTo().window(handle);
            }

            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(lblPatientName));

            Assert.assertTrue(patientName.equals(lblPatientName.getText()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will scroll the Patients Page and verify all the elements are loaded
 * @param driver
------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver scrollPatientsPage(WebDriver driver)
    {
        String patientName = "Episode History & Visit Count";
        try{
            JavascriptExecutor js = ((JavascriptExecutor) driver);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(lblEpisodeHistory));
            Assert.assertTrue(patientName.equals(lblEpisodeHistory.getText().trim()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }
/**-----------------------------------------------------------------------------------------------------------------
 * This method will verify the Recommendation reason of the patient
 * @param driver
------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver verifyRecommendationReason(WebDriver driver) throws NoSuchElementException
    {
        //String noRecommendationReason = lblNoRecommendation.getText();
        try{
            JavascriptExecutor js = ((JavascriptExecutor) driver);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(lblRecommendationTab));

            lblRecommendationTab.click();

            if(lblRecommendationRecord.isDisplayed())
            {
                Assert.assertTrue(true,"Recommendation present");
            }
            else
            {
                Assert.assertFalse(false,"Recommendation reason found");
                Assert.assertTrue(lblNoRecommendation.getText().contains("Visit Recommendations are not supported for this Payor"));
            }
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.assertTrue(lblNoRecommendation.getText().contains("Visit Recommendations are not supported for this Payor"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }
}
