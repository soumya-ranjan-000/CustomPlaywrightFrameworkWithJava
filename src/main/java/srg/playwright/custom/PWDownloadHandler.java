package srg.playwright.custom;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class PWDownloadHandler {
    static String DOWNLOAD_FILE_URL = null;

    public void download(Page page, Locator element, String path, String fileName) {
        Download download = page.waitForDownload(element::click);
        DOWNLOAD_FILE_URL = download.url();
        download.saveAs(Paths.get(path, fileName));
    }

    public void download(Page page, Locator element, String path) {
        Download download = page.waitForDownload(element::click);
        download.saveAs(Paths.get(path, download.suggestedFilename()));
    }
}
