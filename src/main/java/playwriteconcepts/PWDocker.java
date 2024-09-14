package playwriteconcepts;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class PWDocker {
    public static void main(String[] args) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        launchOptions.setChannel("chrome");
        try (Playwright playwright = Playwright.create()) {
            Browser browser = null;
            browser = playwright.chromium().connect("ws://localhost:3000");
            Page page = browser.newContext(new Browser.NewContextOptions().setViewportSize(1463,691)).newPage(); // open a new page
            page.navigate("https://myjob.page/tools/test-files");
        }

    }
}
