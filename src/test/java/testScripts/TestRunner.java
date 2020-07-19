package testScripts;

import commonMethods.CustomReporter;
import org.testng.TestNG;

public class TestRunner {

    static TestNG testNG;

    public static void main(String[] args){

        CustomReporter cr = new CustomReporter();
        testNG = new TestNG();
        testNG.setTestClasses(new Class[]{TestCase1.class});
        testNG.addListener(cr);
        testNG.run();
    }

}
