package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import srg.StartTest;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"classpath:features"},
        glue = {"StepDefinitions"}
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    public static ThreadLocal<StartTest> testRunner = new ThreadLocal<>();

    @Override
    @DataProvider
    public Object[][] scenarios(){
        return super.scenarios();
    }

}
