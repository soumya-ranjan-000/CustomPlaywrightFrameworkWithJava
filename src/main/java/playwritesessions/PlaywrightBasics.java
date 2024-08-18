package playwritesessions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightBasics {
    public static void main(String[] args) {
        String browserName = System.getProperty("BROWSER")==null?"chromium":System.getProperty("BROWSER");
        String headlessMode = System.getProperty("HeadlessMode")==null?"false":System.getProperty("HeadlessMode");
        String channel = System.getProperty("Channel")==null?"chrome":System.getProperty("Channel"); // can be chrome, msedge, chrome-beta, msedge-beta, msedge-dev
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(Boolean.parseBoolean(headlessMode));
        launchOptions.setChannel(channel);
        try (Playwright playwright = Playwright.create()) {
            Browser browser = null;
            if (browserName.equals("chromium")) {
                browser = playwright.chromium().launch(launchOptions);
            } else if (browserName.equals("firefox")) {
                browser = playwright.firefox().launch(launchOptions);
            } else if (browserName.equals("webkit")) {
                browser = playwright.webkit().launch(launchOptions);
            }
            Page page = browser.newPage(); // open a new page
            page.navigate("https://testautomationpractice.blogspot.com/");
            String title = page.title();
            String url = page.url();
            System.out.println("Title: " + title);
            System.out.println("URL: " + url);
        }

//
//        browser.close();
//        playwright.close();
    }
}
