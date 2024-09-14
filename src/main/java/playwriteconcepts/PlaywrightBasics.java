package playwriteconcepts;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class PlaywrightBasics {
    public static void main(String[] args) {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        launchOptions.setChannel("chrome");
        try (Playwright playwright = Playwright.create()) {
            Browser browser = null;
            browser = playwright.chromium().launch(launchOptions);
            Page page = browser.newContext(new Browser.NewContextOptions().setViewportSize(1463,691)).newPage(); // open a new page
            page.navigate("https://myjob.page/tools/test-files");

            Locator ele = page.locator("//a[text()='1MB']");

            // Wait for the download to start
            // Perform the action that initiates download
            Download download = page.waitForDownload(ele::click);
            download.saveAs(Paths.get("./downloadFolder", download.suggestedFilename()));
            download.saveAs(Paths.get("./downloadFolder", "XYZ.zip"));

        }

    }
}
