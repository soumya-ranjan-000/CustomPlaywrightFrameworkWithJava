package srg.playwright.custom;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.Setter;
import org.opentest4j.AssertionFailedError;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.extentreports.LogToConsoleAndHTMLReport;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@Setter
public class CommonOperation extends LocateElementFromPage{

    TestSetup testRunner = CucumberRunner.testRunner.get();

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



    public void isAttached(Locator locator) {
        try {
            assertThat(locator).isAttached();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isChecked(Locator locator) {
        try {
            assertThat(locator).isAttached();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isDisabled(Locator locator) {
        try {
            assertThat(locator).isDisabled();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isEditable(Locator locator) {
        try {
            assertThat(locator).isEditable();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isEmpty(Locator locator) {
        try {
            assertThat(locator).isEmpty();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isEnabled(Locator locator) {
        try {
            assertThat(locator).isEnabled();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isFocused(Locator locator) {
        try {
            assertThat(locator).isFocused();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isHidden(Locator locator) {
        try {
            assertThat(locator).isHidden();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isInViewport(Locator locator) {
        try {
            assertThat(locator).isInViewport();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isVisible(Locator locator) {
        try {
            assertThat(locator).isVisible();
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void isContainsText(Locator locator, String text) {
        try {
            assertThat(locator).containsText(text);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            throw e;
        }
    }

    public void hasAttribute(Locator locator, String attributeName, String value) {
        try {
            assertThat(locator).hasAttribute(attributeName, value);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Name: " + attributeName, "Value: " + value);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Name: " + attributeName, "Value: " + value);
            throw e;
        }
    }

    public void hasClass(Locator locator, String className) {
        try {
            assertThat(locator).hasClass(className);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Class Name: " + className);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Class Name: " + className);
            throw e;
        }
    }

    public void hasCount(Locator locator, int count) {
        try {
            assertThat(locator).hasCount(count);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Count: " + count);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Count: " + count);
            throw e;
        }
    }

    public void hasCSS(Locator locator, String name, String value) {
        try {
            assertThat(locator).hasCSS(name, value);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Name: " + name, "Value: " + value);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Name: " + name, "Value: " + value);
            throw e;
        }
    }

    public void hasId(Locator locator, String id) {
        try {
            assertThat(locator).hasId(id);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Id: " + id);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Locator: " + locator, "Id: " + id);
            throw e;
        }
    }

    public void hasJSProperty(Locator locator, String name, String value) {
        try {
            assertThat(locator).hasJSProperty(name, value);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Name: " + name, "Value: " + value);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Name: " + name, "Value: " + value);
            throw e;
        }
    }

    public void hasRole(Locator locator, AriaRole ariaRole) {
        try {
            assertThat(locator).hasRole(ariaRole);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Role: " + ariaRole.name());
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Role: " + ariaRole.name());
            throw e;
        }
    }

    public void hasText(Locator locator, String text) {
        try {
            assertThat(locator).hasText(text);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Text: " + text);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Text: " + text);
            throw e;
        }
    }

    public void hasValue(Locator locator, String value) {
        try {
            assertThat(locator).hasValue(value);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Value: " + value);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Value: " + value);
            throw e;
        }
    }

    public void hasValues(Locator locator, String[] values) {
        try {
            assertThat(locator).hasValues(values);
            consoleAndHTMLReport.pass(getMethodName(), "Locator: " + locator, "Value: " + values);
        } catch (AssertionFailedError e) {
            if (testRunner.isTakeScreenshotOfEachLocator()) {
                consoleAndHTMLReport.failWithScreenshot(getMethodName(), locator.toString(), locator.screenshot());
                consoleAndHTMLReport.fail(e);
            } else {
                consoleAndHTMLReport.fail(getMethodName(), locator.toString(), e);
            }
            consoleAndHTMLReport.fail(getMethodName(), e, "Locator: " + locator, "Value: " + values);
            throw e;
        }
    }

    public static String getMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[2].getMethodName();
    }
}
