package srg.playwright.custom;

import com.microsoft.playwright.Page;
import lombok.Getter;
import lombok.Setter;
import srg.CucumberRunner;

import java.io.IOException;

public class CommonUIOperation {
    private final LocateElementFromPage locateElementFromPage;
    @Setter
    @Getter
    private Page page;

    public CommonUIOperation() throws IOException {
        this.setPage(CucumberRunner.testRunner.get().getPage());
        locateElementFromPage = new LocateElementFromPage(this.page);
    }

    public LocateElementFromPage getElementLocatorForPage() {
        return locateElementFromPage;
    }
}
