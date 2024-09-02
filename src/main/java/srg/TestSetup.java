package srg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.JsonObject;
import io.cucumber.java.Scenario;
import io.cucumber.java.hu.Ha;
import lombok.Getter;
import lombok.Setter;
import srg.extentreports.ExtentManager;
import srg.playright.base.PlaywrightFactory;
import srg.util.ResourceHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;


public class TestSetup {
    final String PW_PROP_FILE_NAME = "playwright.properties";
    final String PAGE_PROP_FILE_NAME = "page.properties";
    final String PAGE_ELE_FILE_NAME = "PageObjects.yml";
    @Getter
    private static HashMap<String, ExtentReports> extentReportsWithfeature = new HashMap<>();

    @Getter
    public static final ThreadLocal<HashMap<String,ExtentTest>> extentTestWithScenario = new ThreadLocal<>();
    @Getter
    private final boolean isLoggingScreenshotEnabled;
    @Getter
    @Setter
    private Scenario scenario;
    @Getter @Setter
    private String featureFile;
    @Getter @Setter
    private ThreadLocal<String> scenarioName = new ThreadLocal<>();

    @Getter @Setter
    private ExtentTest extentLogger;
    @Getter
    @Setter
    private Properties playwrightProperties;
    @Getter
    @Setter
    private Properties pageProperties;
    @Getter @Setter
    private PlaywrightFactory playwrightFactory;
    @Getter
    @Setter
    private JsonObject pageElements;

    @Getter @Setter
    private String screenshotPath;
    @Getter @Setter
    private boolean takeScreenshotOfEachLocator;

    public TestSetup() throws IOException {
        setPlaywrightProperties(ResourceHandler.getPropertiesFile(PW_PROP_FILE_NAME));
        setPageProperties(ResourceHandler.getPropertiesFile(PAGE_PROP_FILE_NAME));
        setPageElements(ResourceHandler.convertYamlToJsonObject(PAGE_ELE_FILE_NAME));
        this.screenshotPath = this.getPlaywrightProperties().getProperty("screenshotPath");
        this.isLoggingScreenshotEnabled = Boolean.parseBoolean(this.getPlaywrightProperties().getProperty("takeScreenshotForEachStep", "false"));
        this.takeScreenshotOfEachLocator = Boolean.parseBoolean(this.getPlaywrightProperties().getProperty("takeScreenshotOfEachLocator", "false"));
    }

    public void addExtentReportByFeatureFileName(String featureFile) {
        ExtentReports reports = ExtentManager.createExtentReports(featureFile);
        extentReportsWithfeature.put(featureFile, reports);
    }

    public ExtentReports getExtentReportByFeatureFileName(String featureFile) {
        return extentReportsWithfeature.get(featureFile);
    }

}
