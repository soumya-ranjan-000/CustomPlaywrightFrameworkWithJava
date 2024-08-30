package srg.playwright.custom;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import lombok.Setter;


@Setter
public class CommonUIOperation extends LocateElementFromPage {

    private Page page;

    public CommonUIOperation(Page page) {
        super(page);
        this.page = page;
    }

    public CommonUIOperation navigateToUrl(String url) {
        try {
            Response response = page.navigate(url);
            if (response != null) {
                if (response.status() == 200) {
                    testManager.passLog("Navigate To URL.", "URL: %s".formatted(url));
                } else {
                    testManager.failLog("Navigate To URL. URL: %s".formatted(url));
                }
            } else {
                testManager.passLog("Navigate To URL.", "URL: %s".formatted(url));
            }
        } catch (Exception e) {
            testManager.failLog("Navigated To URL Failed. URL: %s".formatted(url), e);
            throw e;
        }
        return this;
    }
}
