package srg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.extentreports.ExtentManager;
import srg.playwright.custom.CommonUIOperation;
import srg.util.ResourceHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class StartTest {
    private CommonUIOperation operation;
    private HashMap<String, String> mainData;
    private final ArrayList<String> allFeatureFileNames = new ArrayList<>();
    private final HashMap<String, ExtentReports> allExtentReports = new HashMap<>();
    private static boolean testsFailed = false;
    private DataTable testData;
    private String team;
    private Browser browser;
    private String OS;
    private String ymlString;
    private String testEnvironment;
    private String projectId;
    private boolean isLoggingScreenshotEnabled;
    private boolean isLoggingScreenshotEnabledOnlyFailureOnly;
    private static final Logger LOGGER = LoggerFactory.getLogger(StartTest.class);
    private boolean failureLogged = false;
    private String duration = null;
    private String threadCount = null;
    private String loopCount = null;
    private String featureName = null;
    private Scenario scenario;
    private ExtentTest extentLogger;
    private Page page;
    private Playwright playwrightServer;
    private Properties playwrightProperties;
    private Properties pageProperties;
    private BrowserType browserType;
    private BrowserContext browserContext;

    public Properties getPlaywrightProperties() {
        return this.playwrightProperties;
    }

    public void setPlaywrightProperties(String fileName) throws IOException {
        this.playwrightProperties = ResourceHandler.getPropertiesFile(fileName);
    }

    public void setPageProperties(String fileName) throws IOException {
        this.pageProperties = ResourceHandler.getPropertiesFile(fileName);
    }

    public Properties getPageProperties() {
        return this.pageProperties;
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public StartTest() {
        this.isLoggingScreenshotEnabled = System.getProperty("loggingScreenshotEnabled") == null ? true :
                Boolean.parseBoolean(System.getProperty("loggingScreenshotEnabled"));
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public ExtentTest getExtentLogger() {
        return extentLogger;
    }

    public void setExtentLogger(ExtentTest extentLogger) {
        this.extentLogger = extentLogger;
    }

    public String getYmlString() {
        return ymlString;
    }

    public void setYmlString(String ymlString) {
        this.ymlString = ymlString;
    }

    public String getTestEnvironment() {
        return testEnvironment;
    }

    public void setTestEnvironment(String testEnvironment) {
        this.testEnvironment = testEnvironment;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public CommonUIOperation getOperation() {
        return operation;
    }

    public void setOperation(CommonUIOperation operation) {
        this.operation = operation;
    }

    public HashMap<String, String> getMainData() {
        return mainData;
    }

    public void setMainData(HashMap<String, String> mainData) {
        this.mainData = mainData;
    }

    public DataTable getTestData() {
        return testData;
    }

    public void setTestData(DataTable testData) {
        this.testData = testData;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public boolean isFailureLogged() {
        return failureLogged;
    }

    public void setFailureLogged(boolean failureLogged) {
        this.failureLogged = failureLogged;
    }

    public void addFeatureFileName(String featureFileName) {
        allFeatureFileNames.add(featureFileName);
    }

    public ArrayList<String> getFeatureFileNames() {
        return allFeatureFileNames;
    }

    public void addExtentReportByFeatureFileName(String featureFile) {
        allExtentReports.put(featureFile, ExtentManager.createExtentReports(featureFile));
    }

    public ExtentReports getExtentReportByFeatureFileName(String featureFile) {
        return allExtentReports.get(featureFile);
    }


    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return this.page;
    }

    public Playwright getPlaywrightServer() {
        return playwrightServer;
    }

    public void setPlaywrightServer(Playwright playwrightServer) {
        this.playwrightServer = playwrightServer;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }

    public BrowserType getBrowserType() {
        return this.browserType;
    }

    public void setBrowserContext(BrowserContext newBrowserContext) {
        this.browserContext = newBrowserContext;
    }

    public BrowserContext getBrowserContext() {
        return this.browserContext;
    }
}
