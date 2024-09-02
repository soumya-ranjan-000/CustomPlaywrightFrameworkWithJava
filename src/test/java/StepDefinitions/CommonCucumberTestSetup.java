package StepDefinitions;

import Runners.CucumberTestRunner;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.exceptions.BrowserTypeNotFoundException;
import srg.playright.base.PlaywrightFactory;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class CommonCucumberTestSetup {
    private final Logger LOGGER = LoggerFactory.getLogger(CommonCucumberTestSetup.class);
    @Before(order = 0)
    public void before(Scenario scenario) throws Exception {
        String scenarioName = scenario.getName();
        String featureFileName = scenario.getUri().toString().split("features/")[1].split("\\.")[0];
        TestSetup myTestRunner = new TestSetup();
        myTestRunner.setFeatureFile(featureFileName);
        myTestRunner.setScenario(scenario);
        myTestRunner.getScenarioName().set(scenarioName);
        if (!TestSetup.getExtentReportsWithfeature().containsKey(scenarioName)) {
            myTestRunner.addExtentReportByFeatureFileName(scenarioName);
        }
        ExtentTest test = myTestRunner.getExtentReportByFeatureFileName(scenarioName).createTest(scenarioName);
        myTestRunner.setExtentLogger(test);
        LOGGER.info("===> Feature: '{}'", featureFileName);
        LOGGER.info("===> Scenario: '{}'", scenarioName);
        CucumberRunner.testRunner.set(myTestRunner);
        CucumberTestRunner.testRunner.set(myTestRunner);
    }

    @After(order = 0)
    public void after(Scenario scenario) {
        TestSetup myTestRunner = CucumberRunner.testRunner.get();
        String featureFileName = scenario.getUri().toString().split("features/")[1].split("\\.")[0];
        if (scenario.isFailed()) {
            ExtentTest extentTest = myTestRunner.getExtentLogger();
            byte[] buffer = myTestRunner.getPlaywrightFactory().getPage().screenshot();
            extentTest.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(buffer));
        }
        var extentReport = myTestRunner.getExtentReportByFeatureFileName(scenario.getName());
        extentReport.flush();
        if (myTestRunner.getPlaywrightFactory().getBrowserContext() != null)
            myTestRunner.getPlaywrightFactory().getBrowserContext().close();
        if (myTestRunner.getPlaywrightFactory().getBrowser() != null)
            myTestRunner.getPlaywrightFactory().getBrowser().close();
        myTestRunner.getPlaywrightFactory().getPlaywright().close();
    }

    @When("Open browser")
    public void openBrowser() throws BrowserTypeNotFoundException, UnsupportedEncodingException {
        var testRunner = CucumberRunner.testRunner.get();
        var playwrightFactory = new PlaywrightFactory();
        playwrightFactory.initializeBrowserProperties(testRunner.getPlaywrightProperties());
        playwrightFactory.startBrowserWithNewConnection();
        playwrightFactory.startNewBrowserContext();
        playwrightFactory.startNewPageFromBrowserContext();
        testRunner.setPlaywrightFactory(playwrightFactory);
    }
}
