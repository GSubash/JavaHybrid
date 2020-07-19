package pageObjects;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

public class ReportsPage {

    WebDriver driver;

    @FindBy(xpath = "//a[contains(text(),'Reports') and @href='/Ebs/Report']")
    public static WebElement lnkCareReports;

    @FindBy(xpath = "//a[contains(text(),'Care Activity Report')]")
    public static WebElement lnkCareActivityReports;

    @FindBy(xpath = "//a[contains(text(),'Care Coverage Report')]")
    public static WebElement lnkCareCoverageReports;

    @FindBy(xpath = "//a[contains(text(),'Care Episode Visit Report')]")
    public static WebElement lnkCareEpisodeReports;

    @FindBy(xpath = "//a[contains(text(),'Care Patient Record View Report')]")
    public static WebElement lnkCarePatientReports;

    public ReportsPage(WebDriver driver)
    {
        this.driver = driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will navigate on the Care Reports page
 * @param driver
------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver clickCareReports(WebDriver driver) throws IOException, InterruptedException
    {
        try {
            //Wait until Care link is visible
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(lnkCareReports));
            lnkCareReports.click();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will download the mentioned report to a excel file
 * @param driver
 * @param reportName
------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver downloadReport(WebDriver driver,String reportName)
    {
        try{
            String fileName = System.getProperty("user.dir")+File.separator+CommonClass.getProperty("downloadFilepath") + File.separator + CommonClass.getProperty("downloadFileName");
            if(Files.exists(Paths.get(fileName)))
            {
                CommonClass.deleteFile(CommonClass.getProperty("downloadFilepath"),CommonClass.getProperty("downloadFileName"));
            }
            switch (reportName)
            {
                case "Activity":
                    lnkCareActivityReports.click();
                    break;
                case "Coverage":
                    lnkCareCoverageReports.click();
                    break;
                case "Episode":
                    lnkCareEpisodeReports.click();
                    break;
                case "Patient":
                    lnkCarePatientReports.click();
                    break;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will verify the dowloaded excel file for recent visits of the patient
------------------------------------------------------------------------------------------------------------------*/
    public static boolean verifyPatientRecord() throws IOException, InterruptedException
    {
        try {
            //waiting for file to be downloaded
            Thread.sleep(5000);
            String fileName = System.getProperty("user.dir")+File.separator+CommonClass.getProperty("downloadFilepath") + File.separator + CommonClass.getProperty("downloadFileName");
            String viewDate = CommonClass.ReadData(fileName,0,1,"ViewDate");
            //viewDate = viewDate.substring(0,8);
            Date recordDate =new SimpleDateFormat("MM/dd/yyyy").parse(viewDate);

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            String date = formatter.format(new Date());
            Date currentDate =new SimpleDateFormat("MM/dd/yyyy").parse(date);

            long diff = currentDate.getTime() - recordDate.getTime();
            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            System.out.println(diffDays);

            if(diffDays > 5 )
            {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
