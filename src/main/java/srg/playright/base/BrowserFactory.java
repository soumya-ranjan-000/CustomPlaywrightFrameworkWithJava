package srg.playright.base;

import com.microsoft.playwright.*;
import lombok.Getter;
import srg.CucumberRunner;
import srg.exceptions.BrowserTypeNotFoundException;
import srg.util.ResourceHandler;

import java.io.IOException;
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

    @Getter
    private Playwright playwrightServer;
    private BrowserType browserType;
    private Browser lclBrowser;
    private BrowserContext browserContext;

    public BrowserFactory() {
        initializeBrowserProperties();
    }

    void initializeBrowserProperties() {
        Properties prop = CucumberRunner.testRunner.get().getBrowserProperties();
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
        Playwright.CreateOptions createOptions = new Playwright.CreateOptions()
                .setEnv(new HashMap<>(System.getenv()));
        return createOptions;
    }

    private Playwright startServer() {
        this.playwrightServer = Playwright.create(getPlaywrightCreateOptions());
        return this.playwrightServer;
    }

    public BrowserType initBrowserType() throws BrowserTypeNotFoundException {
        playwrightServer = this.startServer();
        switch (this.browser) {
            case "chromium" -> {
                this.browserType = playwrightServer.chromium();
            }
            case "webkit" -> {
                this.browserType = playwrightServer.webkit();
            }
            case "firefox" -> {
                this.browserType = playwrightServer.firefox();
            }
            default -> throw new BrowserTypeNotFoundException(this.browser);
        }
        return this.browserType;
    }

    public Browser startBrowserWithNewConnection() throws BrowserTypeNotFoundException {
        this.lclBrowser = initBrowserType().launch(getLaunchOptions());
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
        if(isWindowMaximized){
            contextOptions.setViewportSize(null);
        }else {
            contextOptions.setViewportSize(viewPortWidth, viewPortHeight);
        }
        contextOptions.setScreenSize(viewPortWidth,viewPortHeight);
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
            Browser.NewPageOptions pageOptions = new Browser.NewPageOptions();
            return pageOptions;
        }

        public Page getNewPage(String pagePropertiesFileName) throws IOException {
            return lclBrowser.newPage(getPageOptions(pagePropertiesFileName));
        }

        public Page getNewPageFromBrowserContext() {
            return browserContext.newPage();
        }
    }
}
