package commonMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CommonClass {

    static WebDriver driver;

    public CommonClass(WebDriver driver) {
        this.driver = driver;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method used to get the data from property file
 * @param text
 ------------------------------------------------------------------------------------------------------------------*/

    public static String getProperty(String text) throws IOException {

        File file = new File("src\\test\\resources\\Config.properties");

        FileInputStream fileInput = new FileInputStream(file);

        Properties prop = new Properties();

        prop.load(fileInput);

        text = prop.getProperty(text);

        return text;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method used to start the Browser of choice for Execution
 * @param Browser
------------------------------------------------------------------------------------------------------------------*/
    public WebDriver startbrowser(String Browser) throws IOException
    {
        String iepath = getProperty("IEDriverpath");
        String chromepath = getProperty("ChromeDriverPath");
        String geckopath = getProperty("GeckoDriverPath");

        if(Browser.equalsIgnoreCase("firefox"))
        {
            System.setProperty("webdriver.gecko.driver",geckopath);
            driver = new FirefoxDriver();
        }

        if(Browser.equalsIgnoreCase("ie"))
        {
            System.setProperty("webdriver.ie.driver", iepath);
            DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
            caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            InternetExplorerDriver driver = new InternetExplorerDriver(caps);
        }

        if(Browser.equalsIgnoreCase("chrome"))
        {
            System.setProperty("webdriver.chrome.driver",chromepath);

            String downloadFilepath = System.getProperty("user.dir")+File.separator+ getProperty("downloadFilepath");
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilepath);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", chromePrefs);
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            cap.setCapability(ChromeOptions.CAPABILITY, options);
             driver = new ChromeDriver(cap);
        }

        driver.get(getProperty("baseURL"));
        driver.manage().window().maximize();

        return driver;

    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method used for reading excel data
 *  @param file
 *  @param sheetno
 *  @param rowno
 *  @param colName
------------------------------------------------------------------------------------------------------------------*/

    public static String ReadData(String file,int sheetno, int rowno, String colName) throws FileNotFoundException, IOException {
        String data = null;
        try
        {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(sheetno);
            Hashtable<String, String> input = importxldata(myExcelSheet, rowno);
            data = input.get(colName);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e1)
        {
            e1.printStackTrace();
        }
        return data;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method used for creating Hashtable for reading excel data
 *  @param objsheet
 *  @param rownum
------------------------------------------------------------------------------------------------------------------*/

    public static Hashtable<String, String> importxldata(Sheet objsheet, int rownum) {
        Hashtable<String, String> htable = new Hashtable<String, String>();
        Iterator<Row> iterator = objsheet.iterator();
        Row nextRow = iterator.next();
        int columnCount = nextRow.getLastCellNum();


        for (int data = 0; data < columnCount; data++) {
            DataFormatter formatter = new DataFormatter();
            Cell cell1 = objsheet.getRow(0).getCell(data);
            String value1 = formatter.formatCellValue(cell1);
            Cell cell2 = objsheet.getRow(rownum).getCell(data);
            String value2 = formatter.formatCellValue(cell2);
            htable.put(value1, value2);
        }
        return htable;
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will write data into excel file
 *  @param fileName
 *  @param sheetno
 *  @param rowno
 *  @param cellno
 *  @param value
------------------------------------------------------------------------------------------------------------------*/

    public static void WriteData(String fileName , int sheetno, int rowno, int cellno, String value) throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream(new File(fileName));
        @SuppressWarnings("resource")
        Workbook myExcelBook = new XSSFWorkbook(file);
        Sheet myExcelSheet = myExcelBook.getSheetAt(sheetno);
        Cell cell = myExcelSheet.getRow(rowno).getCell(cellno);
        cell.setCellValue(value);
        file.close();
        FileOutputStream outFile = new FileOutputStream(new File(fileName));
        myExcelBook.write(outFile);
        outFile.close();
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will handle popup alerts
------------------------------------------------------------------------------------------------------------------*/

    public static WebDriver handleAlerts() {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return driver;
    }
/**-----------------------------------------------------------------------------------------------------------------
 * This method will detete file attachment
 *  @param filePath
 *  @param fileName
 ------------------------------------------------------------------------------------------------------------------*/
    public static void deleteFile(String filePath,String fileName) {

        try {
            File afile = new File(System.getProperty("user.dir") + File.separator + filePath + File.separator + fileName);
            if (afile.delete()) {
                System.out.println("File is deleted Successfully...");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**-----------------------------------------------------------------------------------------------------------------
     * This method will sendEmail with file attachment
     *  @param driver
     ------------------------------------------------------------------------------------------------------------------*/
    public static WebDriver closeAllBrowsers(WebDriver driver) {
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            driver.switchTo().window(handle);
            driver.close();
        }
        return driver;
    }

    /**-----------------------------------------------------------------------------------------------------------------
     * This method will take screenshot of the WebDriver Instance
     * @param driver
     * @param fileWithPath
     * @throws Exception
    ------------------------------------------------------------------------------------------------------------------*/
    public static void takeSnapShot(WebDriver driver,String fileWithPath) throws Exception{
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile=new File(fileWithPath);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
    }

/**-----------------------------------------------------------------------------------------------------------------
 * This method will sendEmail with file attachment
 * @param fromUser
 * @param ToUser1
 * @param filePath
------------------------------------------------------------------------------------------------------------------*/

    public static void sendEmail(String fromUser,String ToUser1,String filePath)
    {
        // Create object of Property file
        Properties props = new Properties();

        // this will set host of server- you can change based on your requirement
        props.put("mail.smtp.host", "smtp.gmail.com");

        // set the port of socket factory
        props.put("mail.smtp.socketFactory.port", "465");

        // set socket factory
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // set the authentication to true
        props.put("mail.smtp.auth", "true");

        // set the port of SMTP server
        props.put("mail.smtp.port", "465");

        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(props,

                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(fromUser, "");

                    }

                });

        try {

            // Create object of MimeMessage class
            Message message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromUser));

            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ToUser1));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ToUser2));
//            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CCUser1));
//            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CCUser2));

            // Add the subject link
            message.setSubject("Medalogix Automation Report");

            // Create object to add multimedia type content
            BodyPart messageBodyPart1 = new MimeBodyPart();

            // Set the body of email
            messageBodyPart1.setText("Attached the TestNG Report of current Test Execution...");

            // Create another object to add another content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            // Mention the file which you want to send
            String filename = System.getProperty("user.dir") + File.separator + filePath;

            // Create data source and pass the filename
            DataSource source = new FileDataSource(filename);

            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));

            // set the file
            messageBodyPart2.setFileName(filename);

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();

            // add body part 1
            multipart.addBodyPart(messageBodyPart2);

            // add body part 2
            multipart.addBodyPart(messageBodyPart1);

            // set the content
            message.setContent(multipart);

            // finally send the email
            Transport.send(message);
            System.out.println("=====Email Sent=====");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}