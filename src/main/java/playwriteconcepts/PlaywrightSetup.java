package playwriteconcepts;

import com.microsoft.playwright.*;

public class PlaywrightSetup {
    private Browser browser;
    private Playwright playwright;

    public PlaywrightSetup() {
        this.playwright = Playwright.create();
    }

    public Browser getBrowser() {
        return browser;
    }

    public Browser startBrowserWithOutBrowserContext() {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setChannel("chrome");
        launchOptions.setHeadless(false);
        this.browser = playwright.chromium().launch(launchOptions);
        return this.browser;
    }

    public BrowserContext getNewBrowserContextFromExistingBrowser() {
        return this.browser.newContext(new Browser.NewContextOptions().setViewportSize(1463, 691));
    }

    public BrowserContext getNewBrowserContextFromNewBrowser() {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setChannel("chrome");
        launchOptions.setHeadless(false);
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(launchOptions);
        return this.browser.newContext(new Browser.NewContextOptions().setViewportSize(1463, 691));
    }

    public Page createNewPageFromBrowser() {
        return this.browser.newPage();
    }

    public Playwright getPlaywrightServer() {
        return this.playwright;
    }

    public void stopPlaywright() {
        this.playwright.close();
    }
}
