package commonMethods;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.*;
import org.testng.reporters.SuiteHTMLReporter;
import org.testng.xml.XmlSuite;

public class CustomReporter implements IExecutionListener{

/**-----------------------------------------------------------------------------------------------------------------
 * This method will override onExecutionFinish method to send mails at the end of execution
------------------------------------------------------------------------------------------------------------------*/
    @Override
    public void onExecutionFinish() {
        try {
            CommonClass.sendEmail(CommonClass.getProperty("fromUser"),CommonClass.getProperty("ToUser1"),CommonClass.getProperty("reportPath"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}