package srg.playwright.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.Page;
import srg.CucumberRunner;

public class CommonUIOperation {
    private Page page;
    private LocateElementFromPage locateElementFromPage;

    public CommonUIOperation() throws JsonProcessingException {
        this.setPage(CucumberRunner.testRunner.get().getPage());
        locateElementFromPage = new LocateElementFromPage(this.page);
    }

    public void setPage(Page page){
        this.page = page;
    }

    public Page getPage(){
        return this.page;
    }

    public LocateElementFromPage getElementLocatorForPage() {
        return locateElementFromPage;
    }
}
