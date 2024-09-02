package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import srg.TestSetup;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"classpath:features"},
        glue = {"StepDefinitions"}
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
