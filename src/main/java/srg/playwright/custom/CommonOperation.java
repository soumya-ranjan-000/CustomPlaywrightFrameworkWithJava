package srg.playwright.custom;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import lombok.Getter;
import lombok.Setter;
import org.opentest4j.AssertionFailedError;
import srg.extentreports.LogToConsoleAndHTMLReport;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@Setter
public class CommonOperation extends LocateElementFromPage{

    @Getter
    private Page page;
    LogToConsoleAndHTMLReport consoleAndHTMLReport = new LogToConsoleAndHTMLReport();


    public CommonOperation(Page page) {
        this.page = page;
    }

    public void setHTMLLogger(ExtentTest node) {
        this.consoleAndHTMLReport = new LogToConsoleAndHTMLReport(node);
    }

    public CommonOperation navigateToUrl(String url) {
        try {
            Response response = page.navigate(url);
            if (response != null) {
                if (response.status() == 200) {
                    consoleAndHTMLReport.pass("Navigate To URL", url);
                } else {
                    consoleAndHTMLReport.fail("Navigate To URL", url);
                }
            } else {
                consoleAndHTMLReport.pass("Navigate To URL", url);
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Navigate To URL", url, e);
            throw e;
        }
        return this;
    }

    public CommonOperation navigateBack() {
        try {
            Response response = page.goBack();
            if (response != null) {
                if (response.ok()) consoleAndHTMLReport.pass("navigateBack");
                else consoleAndHTMLReport.fail("navigateBack");
            } else {
                consoleAndHTMLReport.info("navigateBack", "Nothing there to go back.");
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("navigateBack", e);
            throw e;
        }
        return this;
    }

    public String getURL() {
        consoleAndHTMLReport.info("Get URL", "URL Found: " + page.url());
        return page.url();
    }

    public void assertURL(String expectedUrl) {
        try {
            assertThat(page).hasURL(expectedUrl);
            consoleAndHTMLReport.pass("assertURL","Expected URL: "+expectedUrl,"Actual URL: "+page.url());
        } catch (AssertionFailedError e) {
            consoleAndHTMLReport.fail("assertURL",e,"Expected URL: "+expectedUrl,"Actual URL: "+page.url());
            throw e;
        }
    }

    public void assertURL(Pattern expectedUrl) {
        try {
            assertThat(page).hasURL(expectedUrl);
            consoleAndHTMLReport.pass("assertURL","Expected URL Pattern: "+expectedUrl.pattern(),"Actual URL: "+page.url());
        } catch (AssertionFailedError e) {
            consoleAndHTMLReport.fail("assertURL",e,"Expected URL Pattern: "+expectedUrl.pattern(),"Actual URL: "+page.url());
            throw e;
        }
    }

    public void assertTitle(String expectedTitle) {
        try {
            assertThat(page).hasTitle(expectedTitle);
            consoleAndHTMLReport.pass("assertTitle","Expected Title: "+expectedTitle,"Actual Title: "+page.title());
        } catch (AssertionFailedError e) {
            consoleAndHTMLReport.fail("assertTitle",e,"Expected Title: "+expectedTitle,"Actual Title: "+page.title());
            throw e;
        }
    }

    public void assertTitle(Pattern expectedTitle) {
        try {
            assertThat(page).hasURL(expectedTitle);
            consoleAndHTMLReport.pass("assertURL","Expected Title Pattern: "+expectedTitle.pattern(),"Actual Title: "+page.title());
        } catch (AssertionFailedError e) {
            consoleAndHTMLReport.fail("assertURL",e,"Expected Title Pattern: "+expectedTitle.pattern(),"Actual Title: "+page.title());
            throw e;
        }
    }
}
