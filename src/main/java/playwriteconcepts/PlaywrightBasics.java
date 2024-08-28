package playwriteconcepts;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightBasics {
    public static void main(String[] args) {
        String browserName = "chromium";
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        launchOptions.setChannel("chrome");
        try (Playwright playwright = Playwright.create()) {
            Browser browser = null;
            if (browserName.equals("chromium")) {
                browser = playwright.chromium().launch(launchOptions);
            } else if (browserName.equals("firefox")) {
                browser = playwright.firefox().launch(launchOptions);
            } else if (browserName.equals("webkit")) {
                browser = playwright.webkit().launch(launchOptions);
            }
            Page page = browser.newContext(new Browser.NewContextOptions().setViewportSize(1463,691)).newPage(); // open a new page
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
