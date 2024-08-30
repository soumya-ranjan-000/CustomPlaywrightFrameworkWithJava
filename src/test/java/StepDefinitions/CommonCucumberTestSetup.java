package StepDefinitions;

import Runners.CucumberTestRunner;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.StartTest;
import srg.exceptions.BrowserTypeNotFoundException;
import srg.playright.base.BrowserFactory;

import java.io.IOException;
import java.util.Base64;

public class CommonCucumberTestSetup {

    public ExtentTest extentTestLogger;
    private final Logger LOGGER = LoggerFactory.getLogger(CommonCucumberTestSetup.class);



    @Before(order = 0)
    public void before(Scenario scenario) throws IOException {
        CucumberRunner.testRunner.set(new StartTest());
        StartTest myTestRunner = CucumberRunner.testRunner.get();
        myTestRunner.setPlaywrightProperties("playwright.properties");
        myTestRunner.setPageProperties("page.properties");
        CucumberTestRunner.testRunner.set(myTestRunner);
        String scenarioName = scenario.getName();
        String featureFileName = scenario.getUri().toString().split("features/")[1].split("\\.")[0];
        if (myTestRunner.getFeatureFileNames().isEmpty()) {
            myTestRunner.addFeatureFileName(featureFileName);
            myTestRunner.addExtentReportByFeatureFileName(featureFileName);
        } else if (!myTestRunner.getFeatureFileNames().contains(featureFileName)) {
            myTestRunner.addExtentReportByFeatureFileName(featureFileName);
            myTestRunner.addFeatureFileName(featureFileName);
        }
        this.extentTestLogger = myTestRunner.getExtentReportByFeatureFileName(featureFileName).createTest(scenarioName);
        myTestRunner.setScenario(scenario);
        myTestRunner.setExtentLogger(this.extentTestLogger);
        myTestRunner.setPageObjects();
        LOGGER.info("===> Feature: '{}'", featureFileName);
        LOGGER.info("===> Scenario: '{}'", scenarioName);
    }

    @After(order = 0)
    public void after(Scenario scenario) {
        StartTest myTestRunner = CucumberRunner.testRunner.get();
        String featureFileName = scenario.getUri().toString().split("features/")[1].split("\\.")[0];
        if (scenario.isFailed()) {
            ExtentTest extentTest = CucumberRunner.testRunner.get().getExtentLogger();
            byte[] buffer = CucumberRunner.testRunner.get().getPage().screenshot();
            extentTest.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(buffer));
        }
        myTestRunner.getExtentReportByFeatureFileName(featureFileName).flush();
        if(CucumberRunner.testRunner.get().getBrowserContext() != null) CucumberRunner.testRunner.get().getBrowserContext().close();
        if(CucumberRunner.testRunner.get().getBrowser() != null) CucumberRunner.testRunner.get().getBrowser().close();
        CucumberRunner.testRunner.get().getPlaywrightServer().close();
    }


    @Given("Initialize browser and setup test data")
    public void initializeBrowserAndSetupTestData() throws IOException, BrowserTypeNotFoundException {
        var myTestRunner = CucumberRunner.testRunner.get();
        BrowserFactory browserFactory = myTestRunner.getBrowserFactory();
        myTestRunner.setBrowser(browserFactory.startBrowserWithNewConnection());
        myTestRunner.setBrowserContext(browserFactory.getNewBrowserContext());
        myTestRunner.setPlaywrightServer(browserFactory.getPlaywrightServer());
        myTestRunner.setPage(browserFactory.getNewPage());
    }

}
