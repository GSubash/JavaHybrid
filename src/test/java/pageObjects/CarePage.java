package pageObjects;

import java.io.IOException;
import java.util.Set;

import commonMethods.CommonClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static commonMethods.CommonClass.getProperty;

public class CarePage {

    WebDriver driver;

    @FindBy(id = "CareDataProcessed")
    public static WebElement lblDataProcess;

    @FindBy(id = "ProviderOrganizationIds")
    public static WebElement cboBranch;

    @FindBy(xpath = "//a[contains(text(),'Care')]")
    public static WebElement lnkCare;

    @FindBy(xpath = "//button[@class='ms-choice']")
    public static WebElement btnSelectbranch;

    @FindBy(xpath = "//*[@id='summaryPanel1']//table//tr[1]/td[5]")
    public static WebElement lblBranchName;

    @FindBy(xpath = "//*[@id='summaryPanel1']//table//tr[1]/td[2]/a")
    public static WebElement lnkPatientName;

    @FindBy(xpath = "//table/tbody/tr/td[1]/div[2]")
    public static WebElement lblPatientName;

    @FindBy(xpath = "//div[@class='ms-search']/input")
    public static WebElement txtBranchName;

    @FindBy(xpath = "//div[@class='ms-search']//following::ul/li/label")
    public static WebElement lblSelectBranch;

    @FindBy(xpath = "//div[2]/div/div/div[8]/div[1]")
    public static WebElement lblEpisodeHistory;

    public CarePage(WebDriver driver)
    {
        this.driver = driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will navigate te application to Care Page
 * @param driver
 ------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver navigateToCare(WebDriver driver) throws IOException, InterruptedException
    {
        try {
            //Wait until Care link is visible
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(lnkCare));
            lnkCare.click();
            //Wait until Data Processing Status label is visible
            wait.until(ExpectedConditions.visibilityOf(lblDataProcess));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }
/**-----------------------------------------------------------------------------------------------------------------
 * This method will select the branch specified
 * @param driver
 ------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver selectBranch(WebDriver driver)
    {
        String branchName = "ALBANY, NY HOME HEALTH (LAN)";
        try{
            btnSelectbranch.click();
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.elementToBeClickable(txtBranchName));
            txtBranchName.click();
            txtBranchName.sendKeys(branchName);
            wait.until(ExpectedConditions.elementToBeClickable(lblSelectBranch));
            lblSelectBranch.click();
            wait.until(ExpectedConditions.visibilityOf(lblBranchName));
            Assert.assertTrue(branchName.equals(lblBranchName.getText()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }


}
