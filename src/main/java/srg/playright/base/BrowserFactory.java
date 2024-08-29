package srg.playright.base;

import com.microsoft.playwright.*;
import lombok.Getter;
import srg.CucumberRunner;
import srg.exceptions.BrowserTypeNotFoundException;
import srg.util.ResourceHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class BrowserFactory {

    private String browser;
    private String channel;
    private boolean headless;
    private boolean isWindowMaximized;
    private boolean javaScriptEnabled;
    private boolean isChromiumSandbox;
    private String downloadsPath;
    private Double slowMo;
    private Double timeOut;
    private Integer viewPortWidth;
    private Integer viewPortHeight;
    private String userAgent;
    private String timezone;
    private Double latitude;
    private Double longitude;
    private String permissions;
    private boolean isTracingEnabled;
    private String localeLanguage;
    private String args;
    private boolean isCrossBrowserTestingIsEnabled;
    private String crossBrowserTestingEnv;

    @Getter
    private Playwright playwrightServer;
    private BrowserType browserType;
    private Browser lclBrowser;
    private BrowserContext browserContext;

    public BrowserFactory() {
        initializeBrowserProperties();
    }

    void initializeBrowserProperties() {
        Properties prop = CucumberRunner.testRunner.get().getPlaywrightProperties();
        this.isCrossBrowserTestingIsEnabled = Boolean.parseBoolean(prop.getProperty("CrossBrowserTestingEnabled"));
        this.crossBrowserTestingEnv = prop.getProperty("crossBrowserEnv");
        this.browser = prop.getProperty("browserType");
        this.channel = prop.getProperty("channel");
        this.headless = Boolean.parseBoolean(prop.getProperty("headless"));
        this.isWindowMaximized = Boolean.parseBoolean(prop.getProperty("maximized"));
        this.javaScriptEnabled = Boolean.parseBoolean(prop.getProperty("javaScriptEnabled"));
        this.isChromiumSandbox = Boolean.parseBoolean(prop.getProperty("ChromiumSandbox"));
        this.downloadsPath = prop.getProperty("DownloadsPath");
        this.slowMo = Double.parseDouble(prop.getProperty("slowMo"));
        this.timeOut = Double.parseDouble(prop.getProperty("timeout"));
        this.viewPortWidth = Integer.parseInt(prop.getProperty("viewportWidth"));
        this.viewPortHeight = Integer.parseInt(prop.getProperty("viewportHeight"));
        this.userAgent = prop.getProperty("userAgent");
        this.timezone = prop.getProperty("timezone");
        this.latitude = Double.parseDouble(prop.getProperty("geolocationLatitude"));
        this.longitude = Double.parseDouble(prop.getProperty("geolocationLongitude"));
        this.localeLanguage = prop.getProperty("language");
        this.permissions = prop.getProperty("permissions");
        this.isTracingEnabled = Boolean.parseBoolean(prop.getProperty("tracingEnabled"));
        this.args = prop.getProperty("args");

    }

    private Playwright.CreateOptions getPlaywrightCreateOptions() {
        // Create Playwright with custom options
        return new Playwright.CreateOptions()
                .setEnv(new HashMap<>(System.getenv()));
    }

    private Playwright startServer() {
        this.playwrightServer = Playwright.create(getPlaywrightCreateOptions());
        return this.playwrightServer;
    }

    public BrowserType initBrowserType() throws BrowserTypeNotFoundException {
        this.playwrightServer = this.startServer();
        switch (this.browser) {
            case "chromium" -> {
                return this.playwrightServer.chromium();
            }
            case "webkit" -> {
                return this.playwrightServer.webkit();
            }
            case "firefox" -> {
                return this.playwrightServer.firefox();
            }
            default -> throw new BrowserTypeNotFoundException(this.browser);
        }
    }

    public Browser startBrowserWithNewConnection() throws BrowserTypeNotFoundException, UnsupportedEncodingException {
        this.browserType = this.initBrowserType();
        if (this.isCrossBrowserTestingIsEnabled) {
            switch (this.crossBrowserTestingEnv.toLowerCase()) {
                case "browserstack":
                    this.lclBrowser = BrowserStack.connect(this.browserType);
                    break;
                case "lambdatest":
                    this.lclBrowser = LambdaTest.connect(this.browserType);
                    break;
            }
        } else {
            this.lclBrowser = this.browserType.launch(getLaunchOptions());
        }
        return lclBrowser;
    }

    public BrowserContext getNewBrowserContext() {
        this.browserContext = this.lclBrowser.newContext(getBrowserContextOptions());
        return this.browserContext;
    }

    BrowserType.LaunchOptions getLaunchOptions() {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(this.headless);
        launchOptions.setChannel(this.channel);
        launchOptions.setChromiumSandbox(this.isChromiumSandbox);
        launchOptions.setSlowMo(this.slowMo);
        launchOptions.setTimeout(this.timeOut);
        launchOptions.setDownloadsPath(Path.of(this.downloadsPath));
        launchOptions.setEnv(new HashMap<>(System.getenv()));
        launchOptions.setTracesDir(null);
        launchOptions.setArgs(Arrays.asList(this.args.split(",")));
        return launchOptions;
    }

    Browser.NewContextOptions getBrowserContextOptions() {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        if (isWindowMaximized) {
            contextOptions.setViewportSize(null);
        } else {
            contextOptions.setViewportSize(viewPortWidth, viewPortHeight);
        }
        contextOptions.setScreenSize(viewPortWidth, viewPortHeight);
        contextOptions.setAcceptDownloads(true);
        contextOptions.setJavaScriptEnabled(javaScriptEnabled);
        contextOptions.setLocale(this.localeLanguage);
        contextOptions.setGeolocation(this.latitude, this.longitude);
        contextOptions.setTimezoneId(this.timezone);
        contextOptions.setPermissions(Arrays.asList(this.permissions.split(",")));
        contextOptions.setUserAgent(this.userAgent);
        return contextOptions;
    }


    public class PageFactory {

        Browser.NewPageOptions getPageOptions(String fileName) throws IOException {
            Properties prop = ResourceHandler.getPropertiesFile(fileName);
            return new Browser.NewPageOptions();
        }

        public Page getNewPage(String pagePropertiesFileName) throws IOException {
            return lclBrowser.newPage(getPageOptions(pagePropertiesFileName));
        }

        public Page getNewPageFromBrowserContext() {
            return browserContext.newPage();
        }
    }
}
