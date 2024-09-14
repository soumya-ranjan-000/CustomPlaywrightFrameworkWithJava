package StepDefinitions;

import Runners.CucumberTestRunner;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.exceptions.BrowserTypeNotFoundException;
import srg.playright.base.PlaywrightFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class CommonCucumberTestSetup {
    private final Logger LOGGER = LoggerFactory.getLogger(CommonCucumberTestSetup.class);

    public static void markTestStatus(String status, String reason, Page page) {
        TestSetup myTestRunner = CucumberRunner.testRunner.get();
        if (myTestRunner.getPlaywrightProperties().getProperty("crossBrowserEnv").equals("browserstack")) {
            page.evaluate("_ => {}", "browserstack_executor: { \"action\": \"setSessionStatus\"," +
                    " \"arguments\": { \"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}");
        }
        if (myTestRunner.getPlaywrightProperties().getProperty("crossBrowserEnv").equals("lambdatest")) {
            page.evaluate("_ => {}", "lambdatest_action: { \"action\": \"setTestStatus\"," +
                    " \"arguments\": { \"status\": \"" + status + "\", \"remark\": \"" + reason + "\"}}");
        }
    }

    @Before(order = 0)
    public void browserSetup() throws IOException, BrowserTypeNotFoundException {
        TestSetup myTestRunner = new TestSetup();
        CucumberRunner.testRunner.set(myTestRunner);
        CucumberTestRunner.testRunner.set(myTestRunner);
        var testRunner = CucumberRunner.testRunner.get();
        var playwrightFactory = new PlaywrightFactory();
        playwrightFactory.initializeBrowserProperties(testRunner.getPlaywrightProperties());
        playwrightFactory.startBrowserWithNewConnection();
        playwrightFactory.startNewBrowserContext();
        playwrightFactory.startNewPageFromBrowserContext();
        testRunner.setPlaywrightFactory(playwrightFactory);
    }

    @Before(order = 1)
    public void before(Scenario scenario) throws Exception {
        String scenarioName = scenario.getName();
        String featureFileName = scenario.getUri().toString().split("features/")[1].split("\\.")[0];
        TestSetup myTestRunner = CucumberRunner.testRunner.get();
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
    }

    @After(order = 1)
    public void after(Scenario scenario) {
        TestSetup myTestRunner = CucumberRunner.testRunner.get();
        String screenshotPath = myTestRunner.getScreenshotPath() + "\\" + scenario.getName() + ".png";
        if (scenario.isFailed()) {
            ExtentTest extentTest = myTestRunner.getExtentLogger();
            myTestRunner.getPlaywrightFactory().getPage().screenshot(
                    new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
            extentTest.addScreenCaptureFromPath(scenario.getName() + ".png");
            markTestStatus("failed", null, myTestRunner.getPlaywrightFactory().getPage());
        } else {
            markTestStatus("passed", null, myTestRunner.getPlaywrightFactory().getPage());
        }
        var extentReport = myTestRunner.getExtentReportByFeatureFileName(scenario.getName());
        extentReport.flush();
    }

    @After(order = 0)
    public void endTest() {
        TestSetup myTestRunner = CucumberRunner.testRunner.get();
        if (myTestRunner.getPlaywrightFactory().getBrowserContext() != null)
            myTestRunner.getPlaywrightFactory().getBrowserContext().close();
        if (myTestRunner.getPlaywrightFactory().getBrowser() != null)
            myTestRunner.getPlaywrightFactory().getBrowser().close();
        myTestRunner.getPlaywrightFactory().getPlaywright().close();
    }
}
