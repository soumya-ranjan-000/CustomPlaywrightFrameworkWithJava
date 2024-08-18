package playwritesessions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class Setup {
    private BrowserContext context;
    private Browser browser;
    private Playwright playwright;
    public Setup() throws InterruptedException {

    }

    public BrowserContext getContext() {
        return context;
    }

    public Browser getBrowser() {
        return browser;
    }

    public BrowserContext startBrowser(){
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setChannel("chrome");
        launchOptions.setHeadless(false);
        this.playwright = Playwright.create();
        var browser = playwright.chromium().launch(launchOptions);
        var context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1463, 691));
        return context;
    }

    public void stopPlaywright(){
        this.playwright.close();
    }
}
