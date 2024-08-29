package srg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.Scenario;
import lombok.Getter;
import lombok.Setter;
import srg.extentreports.ExtentManager;
import srg.playwright.custom.CommonUIOperation;
import srg.util.ResourceHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class StartTest {
    @Setter
    @Getter
    private CommonUIOperation operation;
    private final ArrayList<String> allFeatureFileNames = new ArrayList<>();
    private final HashMap<String, ExtentReports> allExtentReports = new HashMap<>();
    @Setter
    @Getter
    private Browser browser;
    @Setter
    @Getter
    private String OS;
    private boolean isLoggingScreenshotEnabled;
    private boolean isLoggingScreenshotEnabledOnlyFailureOnly;
    @Setter
    @Getter
    private Scenario scenario;
    @Getter
    @Setter
    private ExtentTest extentLogger;
    @Getter
    @Setter
    private Page page;
    @Setter
    @Getter
    private Playwright playwrightServer;
    @Getter
    private Properties playwrightProperties;
    private Properties pageProperties;
    @Getter
    @Setter
    private BrowserContext browserContext;

    public void setPlaywrightProperties(String fileName) throws IOException {
        this.playwrightProperties = ResourceHandler.getPropertiesFile(fileName);
    }

    public void setPageProperties(String fileName) throws IOException {
        this.pageProperties = ResourceHandler.getPropertiesFile(fileName);
    }

    public StartTest() {
        this.isLoggingScreenshotEnabled = System.getProperty("loggingScreenshotEnabled") == null || Boolean.parseBoolean(System.getProperty("loggingScreenshotEnabled"));
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
}
