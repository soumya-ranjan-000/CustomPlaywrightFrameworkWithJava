package StepDefinitions;

import Runners.CucumberTestRunner;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.StartTest;
import srg.exceptions.BrowserTypeNotFoundException;
import srg.playright.base.BrowserFactory;
import srg.playwright.custom.CommonUIOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class CommonCucumberTestSetup {

    private static ArrayList<String> allFeatureFileNames = new ArrayList<>();
    private static HashMap<String, ExtentReports> allExtentReports = new HashMap<String, ExtentReports>();
    private static String pomVersion = null;
    private static Integer rerunFailedTestsCount = 0;
    private static Integer buildIteration = 0;
    private static boolean testsFailed = false;
    public ExtentReports extentReports;
    public ExtentTest extentTestLogger;
    public Scenario scenario;
    private final Logger LOGGER = LoggerFactory.getLogger(CommonCucumberTestSetup.class);
    private static final String REPORT_DATETIME_FORMAT = "dd_MMM_HH_mm_ss";
    private static final String REPORT_DATETIME_REGEX = "_\\d{2}_[a-zA-Z]{3}_\\d{2}\\d{2}\\d{2}.html$";
    private static final String HTML_EXTENSION = ".html";
    private static int counter = 0;
    private static List<String> steps = new ArrayList();
    private static List<String> results = new ArrayList();
    private static List<PickleStepTestStep> stepDefs = new ArrayList<>();
    public String scenarioNameForReport = "";
    public String getFailure = "";


    @Before(order = 0)
    public void before(Scenario scenario) throws IOException {
        CucumberRunner.testRunner.set(new StartTest());
        StartTest myTestRunner = CucumberRunner.testRunner.get();
        myTestRunner.setBrowserProperties("playwright.properties");
        myTestRunner.setPageProperties("page.properties");
        CucumberTestRunner.testRunner.set(myTestRunner);
        String scenarioName = scenario.getName();
        String featureFileName = scenario.getUri().toString().split("features/")[1].split("\\.")[0];
        if (StartTest.getFeatureFileNames().size() == 0) {
            StartTest.addFeatureFileName(featureFileName);
            StartTest.addExtentReportByFeatureFileName(featureFileName);
        } else if (!StartTest.getFeatureFileNames().contains(featureFileName)) {
            StartTest.addExtentReportByFeatureFileName(featureFileName);
            StartTest.addFeatureFileName(featureFileName);
        }
        this.extentTestLogger = StartTest.getExtentReportByFeatureFileName(featureFileName).createTest(scenarioName);
        myTestRunner.setScenario(scenario);
        myTestRunner.setExtentLogger(this.extentTestLogger);
        LOGGER.info("===> Feature: '{}' <===", featureFileName);
        LOGGER.info("===> Scenario: '{}' <===", scenarioName);
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
        BrowserFactory browserFactory = new BrowserFactory();
        CucumberRunner.testRunner.get().setBrowser(browserFactory.startBrowserWithNewConnection());
        CucumberRunner.testRunner.get().setBrowserContext(browserFactory.getNewBrowserContext());
        CucumberRunner.testRunner.get().setPlaywrightServer(browserFactory.getPlaywrightServer());
        BrowserFactory.PageFactory pageFactory = browserFactory.new PageFactory();
        CucumberRunner.testRunner.get().setPage(pageFactory.getNewPageFromBrowserContext());
        CucumberRunner.testRunner.get().setOperation(new CommonUIOperation());
    }

}
