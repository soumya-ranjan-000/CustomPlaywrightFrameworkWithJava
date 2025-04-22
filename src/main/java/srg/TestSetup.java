package srg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.JsonObject;
import io.cucumber.java.Scenario;
import srg.extentreports.ExtentManager;
import srg.playright.base.PlaywrightFactory;
import srg.util.ResourceHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class TestSetup {
    final String PW_PROP_FILE_NAME = "playwright.properties";
    final String PAGE_PROP_FILE_NAME = "page.properties";
    final String PAGE_ELE_FILE_NAME = "PageObjects.yml";

    private static final HashMap<String, ExtentReports> extentReportsWithfeature = new HashMap<>();
    public static final ThreadLocal<HashMap<String,ExtentTest>> extentTestWithScenario = new ThreadLocal<>();
    private final boolean isLoggingScreenshotEnabled;
    private Scenario scenario;
    private String featureFile;
    private ThreadLocal<String> scenarioName = new ThreadLocal<>();
    private ExtentTest extentLogger;
    private Properties playwrightProperties;
    private Properties pageProperties;
    private PlaywrightFactory playwrightFactory;
    private JsonObject pageElements;
    private String screenshotPath;
    private boolean takeScreenshotOfEachLocator;

    public TestSetup() throws IOException {
        this.setPlaywrightProperties(ResourceHandler.getPropertiesFile(PW_PROP_FILE_NAME));
        setPageProperties(ResourceHandler.getPropertiesFile(PAGE_PROP_FILE_NAME));
        setPageElements(ResourceHandler.convertYamlToJsonObject(PAGE_ELE_FILE_NAME));
        this.screenshotPath = this.getPlaywrightProperties().getProperty("screenshotPath");
        this.isLoggingScreenshotEnabled = Boolean.parseBoolean(this.getPlaywrightProperties().getProperty("takeScreenshotForEachStep", "false"));
        this.takeScreenshotOfEachLocator = Boolean.parseBoolean(this.getPlaywrightProperties().getProperty("takeScreenshotOfEachLocator", "false"));
    }

    public static Map<String, ExtentReports> getExtentReportsWithfeature() {
        return extentReportsWithfeature;
    }

    public Properties getPlaywrightProperties() {
        return this.playwrightProperties;
    }

    private void setPageElements(JsonObject jsonObject) {
        this.pageElements = jsonObject;
    }

    private void setPageProperties(Properties propertiesFile) {
        this.pageProperties = propertiesFile;
    }

    private void setPlaywrightProperties(Properties propertiesFile) {
        this.playwrightProperties = propertiesFile;
    }

    public void addExtentReportByFeatureFileName(String featureFile) throws IOException {
        ExtentReports reports = ExtentManager.createExtentReports(featureFile);
        extentReportsWithfeature.put(featureFile, reports);
    }

    public ExtentReports getExtentReportByFeatureFileName(String featureFile) {
        return extentReportsWithfeature.get(featureFile);
    }

    public boolean isTakeScreenshotOfEachLocator() {
        return this.takeScreenshotOfEachLocator;
    }

    public PlaywrightFactory getPlaywrightFactory() {
        return this.playwrightFactory;
    }

    public void setFeatureFile(String featureFileName) {
        this.featureFile = featureFileName;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public ThreadLocal<String> getScenarioName() {
        return this.scenarioName;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public ExtentTest getExtentLogger() {
        return this.extentLogger;
    }

    public JsonObject getPageElements() {
        return this.pageElements;
    }

    public void setPlaywrightFactory(PlaywrightFactory playwrightFactory) {
        this.playwrightFactory = playwrightFactory;
    }

    public void setExtentLogger(ExtentTest test) {
        this.extentLogger = test;
    }

    public String getScreenshotPath() {
        return this.screenshotPath;
    }
}
