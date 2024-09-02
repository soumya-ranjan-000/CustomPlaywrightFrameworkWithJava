package srg.playright.base;

import com.microsoft.playwright.*;
import srg.exceptions.BrowserTypeNotFoundException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class PlaywrightFactory {

    /* Browser and Playwright properties */
    private String browserTypeName;
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
    private String localeLanguage;
    private String args;
    private boolean isCrossBrowserTestingIsEnabled;
    private String crossBrowserTestingEnv;
    /* ----------------------------------------------- */

    public PlaywrightFactory() {

    }

    /* get local copy of browsercontext, browser, page, and pw object*/

    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlpage = new ThreadLocal<>();

    public Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public Browser getBrowser() {
        return tlBrowser.get();
    }

    public BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public Page getPage() {
        return tlpage.get();
    }
    /* ----------------------------------------------- */


    public void initializeBrowserProperties(Properties prop) {
        this.isCrossBrowserTestingIsEnabled = Boolean.parseBoolean(prop.getProperty("CrossBrowserTestingEnabled"));
        this.crossBrowserTestingEnv = prop.getProperty("crossBrowserEnv");
        this.browserTypeName = prop.getProperty("browserType");
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
        this.args = prop.getProperty("args");
    }

    private Playwright.CreateOptions getPlaywrightCreateOptions() {
        // Create Playwright with custom options
        return new Playwright.CreateOptions()
                .setEnv(new HashMap<>(System.getenv()));
    }

    private void startPlaywright() {
        tlPlaywright.set(Playwright.create(getPlaywrightCreateOptions()));
    }

    private BrowserType initBrowserType() throws BrowserTypeNotFoundException {
        startPlaywright();
        switch (this.browserTypeName) {
            case "chromium" -> {
                return tlPlaywright.get().chromium();
            }
            case "webkit" -> {
                return tlPlaywright.get().webkit();
            }
            case "firefox" -> {
                return tlPlaywright.get().firefox();
            }
            default -> throw new BrowserTypeNotFoundException(this.browserTypeName);
        }
    }

    public void startBrowserWithNewConnection() throws BrowserTypeNotFoundException, UnsupportedEncodingException {
        BrowserType browserType = initBrowserType();
        if (this.isCrossBrowserTestingIsEnabled) {
            switch (this.crossBrowserTestingEnv.toLowerCase()) {
                case "browserstack":
                    tlBrowser.set(BrowserStack.connect(browserType));
                    break;
                case "lambdatest":
                    tlBrowser.set(LambdaTest.connect(browserType));
                    break;
            }
        } else {
            tlBrowser.set(browserType.launch(getLaunchOptions()));
        }
    }

    public void startNewBrowserContext() {
        tlBrowserContext.set(tlBrowser.get().newContext(getBrowserContextOptions()));
    }

    BrowserType.LaunchOptions getLaunchOptions() {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(this.headless);
        if (this.browserTypeName.equals("chromium")) {
            launchOptions.setChannel(this.channel);
            launchOptions.setChromiumSandbox(this.isChromiumSandbox);
        }
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

    public void initPageProperties(Properties prop) {

    }

    Browser.NewPageOptions getPageOptions() throws IOException {
        return new Browser.NewPageOptions();
    }

    public void startNewPage() throws IOException {
        tlpage.set(tlBrowser.get().newPage(getPageOptions()));
    }

    public void startNewPageFromBrowserContext() {
        tlpage.set(tlBrowserContext.get().newPage());
    }
}
