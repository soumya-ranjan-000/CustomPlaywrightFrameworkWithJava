package playwriteconcepts;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class PWLocators {
    public static void main(String[] args) throws InterruptedException {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setChannel("chrome");
        launchOptions.setHeadless(false);

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(launchOptions);
            BrowserContext context =browser.newContext(new Browser.NewContextOptions().setViewportSize(1463,691));
            Page page =context.newPage();
            page.navigate("https://ecommerce-playground.lambdatest.io/");
            //Locating elements
            // 1. Page.getByRole
            Locator searchBtn = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Search"));
            searchBtn.click();
            Thread.sleep(4000L);
            System.out.println(page.url());
        }
    }
}