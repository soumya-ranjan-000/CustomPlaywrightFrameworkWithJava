package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import srg.TestSetup;

@CucumberOptions(
        features = {"classpath:features"},
        glue = {"StepDefinitions"},
        plugin = {
                "pretty",                             // For readable console output
                "html:target/cucumber-reports.html",  // HTML report
                "json:target/cucumber-reports.json",  // JSON report
                "junit:target/cucumber-reports.xml"   // JUnit XML report
        },
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    public static ThreadLocal<TestSetup> testRunner;

    static{
       testRunner = new ThreadLocal<>();
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }

}
