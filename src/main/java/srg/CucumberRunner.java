package srg;

import io.cucumber.testng.AbstractTestNGCucumberTests;

public class CucumberRunner extends AbstractTestNGCucumberTests {
    public static ThreadLocal<StartTest> testRunner = new ThreadLocal<>();
}
