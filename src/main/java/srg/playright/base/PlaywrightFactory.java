package srg.playright.base;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.exceptions.BrowserTypeNotFoundException;

import java.io.IOException;
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

    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlpage = new ThreadLocal<>();
    private final Logger LOGGER = LoggerFactory.getLogger(PlaywrightFactory.class);

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
        if (System.getProperties().containsKey("Enable_Cross_Browser_Testing") && (System.getProperty("Enable_Cross_Browser_Testing") != null)) {
            this.isCrossBrowserTestingIsEnabled = Boolean.parseBoolean(System.getProperty("Enable_Cross_Browser_Testing"));
        } else {
            this.isCrossBrowserTestingIsEnabled = Boolean.parseBoolean(prop.getProperty("CrossBrowserTestingEnabled", "true"));
        }
        if (System.getProperties().containsKey("Browser_Env") && (System.getProperty("Browser_Env") != null)) {
            this.crossBrowserTestingEnv = System.getProperty("Browser_Env");
        } else {
            this.crossBrowserTestingEnv = prop.getProperty("crossBrowserEnv", "lambdatest");
        }
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
        LOGGER.info("Initialized Playwright Browser Properties Successfully.");
    }

    private Playwright.CreateOptions getPlaywrightCreateOptions() {
        // Create Playwright with custom options
        return new Playwright.CreateOptions()
                .setEnv(new HashMap<>(System.getenv()));
    }

    private void startPlaywright() throws IOException {
        setPlaywrightEnvironment();
        tlPlaywright.set(Playwright.create(getPlaywrightCreateOptions()));
        LOGGER.info("Started Playwright Successfully.");
    }

    private void setPlaywrightEnvironment() throws IOException {
        String variableName = "PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD";
        String variableValue = "1";

        // Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "setx", variableName, variableValue);
            Process p = pb.start();
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Unix-like systems
        else {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "export " + variableName + "=" + variableValue);
            Process p = pb.start();
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private BrowserType initBrowserType() throws BrowserTypeNotFoundException, IOException {
        startPlaywright();
        LOGGER.info("Browser Type: " + this.browserTypeName);
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

    public void startBrowserWithNewConnection() throws BrowserTypeNotFoundException, IOException {
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
            LOGGER.info("Connecting to {}....", this.crossBrowserTestingEnv.toUpperCase());
        } else {
            tlBrowser.set(browserType.launch(getLaunchOptions()));
        }
        if (tlBrowser.get().isConnected()) LOGGER.info("Connection to the browser is successfull.");
        else LOGGER.info("Connection to the browser is unsuccessfull.");
    }

    public void startNewBrowserContext() {
        tlBrowserContext.set(tlBrowser.get().newContext(getBrowserContextOptions()));
        LOGGER.info("Started Browser Context successfully.");
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
        LOGGER.info("New Page Created successfully.");
    }
}
