package pageObjects;

import java.io.IOException;
import java.util.Set;

import commonMethods.CommonClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static commonMethods.CommonClass.getProperty;

public class LogOnPage {

    WebDriver driver;

    @FindBy(id = "Email")
    public static WebElement txtEmail;

    @FindBy(id = "Password")
    public static WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Log On']")
    public static WebElement btnLogon;

    public LogOnPage(WebDriver driver)
    {
        this.driver = driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will login to the application with the provided username and password
 * @param driver
------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver Login(WebDriver driver) throws IOException, InterruptedException
    {
        String email = getProperty("email");
        String password = getProperty("password");
        try {
            //Wait until logon button is visible
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(btnLogon));

            txtEmail.sendKeys(email);
            txtPassword.sendKeys(password);

            btnLogon.click();
            }
        catch (Exception e) {
                e.printStackTrace();
            }
            return driver;
    }
}
