package srg.playwright.custom;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.exceptions.ElementNotFoundException;
import srg.exceptions.InvalidPropertiesException;
import srg.exceptions.MultipleElementsFoundException;
import srg.extentreports.LogToConsoleAndHTMLReport;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LocateElementFromPage implements ElementLocator {

    TestSetup runner = CucumberRunner.testRunner.get();
    private final Page page = runner.getPlaywrightFactory().getPage();

    LogToConsoleAndHTMLReport consoleAndHTMLReport = new LogToConsoleAndHTMLReport();

    public void setHTMLLogger(ExtentTest node) {
        consoleAndHTMLReport = new LogToConsoleAndHTMLReport(node);
    }

    void printCommonLog(String pageName, String elementName) {
        consoleAndHTMLReport.info("Element Need To Be Locate:");
        consoleAndHTMLReport.info("[ Page Name: %s, Element Name: %s ]".formatted(pageName, elementName));
    }

    public Locator locateElementByRole(String pageName, String elementName) throws Exception {
        Page.GetByRoleOptions roleOptions = new Page.GetByRoleOptions();
        return locateElementByRole(pageName, elementName, roleOptions);
    }

    public List<Locator> locateElementsByRole(String pageName, String elementName) throws Exception {
        Page.GetByRoleOptions roleOptions = new Page.GetByRoleOptions();
        return locateElementsByRole(pageName, elementName, roleOptions);
    }

    public Locator locateElementByRole(String pageName, String elementName, Page.GetByRoleOptions roleOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            String elementValue = elementObj.get("Value").getAsString();
            String type = elementObj.get("Type").getAsString();
            // Validate 'Type' and 'AriaRoleType' in one go
            if (!"role".equalsIgnoreCase(type) || !elementObj.has("AriaRoleType")) {
                throw new InvalidPropertiesException("Type: Role is needed for this operation. Operation name: 'locateElementByRole'");
            }
            String ariaRole = elementObj.get("AriaRoleType").getAsString();
            if (isValidPattern(elementValue)) {
                roleOptions.setName(Pattern.compile(elementValue));
            } else {
                roleOptions.setName(elementValue);
            }
            Locator locator = page.getByRole(AriaRole.valueOf(ariaRole.toUpperCase()), roleOptions);
            if (locator.count() > 0) {
                if (locator.count() == 1) {
                    if (runner.isTakeScreenshotOfEachLocator()) {
                        consoleAndHTMLReport.passWithScreenshot("Locate Element By Role", "Element Located Successfully.", locator.screenshot());
                    } else {
                        consoleAndHTMLReport.pass("Locate Element By Role", "Element Located Successfully.");
                    }
                    return locator;
                } else {
                    throw new MultipleElementsFoundException("More than one element found. Expected: 1, Actual: " + locator.count() + " Details: " + Arrays.toString(locator.all().toArray()));
                }
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Role", e.getMessage(), e);
            throw e;
        }
    }

    public List<Locator> locateElementsByRole(String pageName, String elementName, Page.GetByRoleOptions roleOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            String elementValue = elementObj.get("Value").getAsString();
            String type = elementObj.get("Type").getAsString();
            // Validate 'Type' and 'AriaRoleType' in one go
            if (!"role".equalsIgnoreCase(type) || !elementObj.has("AriaRoleType")) {
                throw new InvalidPropertiesException("Type: Role is needed for this operation. Operation name: 'locateElementByRole'");
            }
            String ariaRole = elementObj.get("AriaRoleType").getAsString();
            if (isValidPattern(elementValue)) {
                roleOptions.setName(Pattern.compile(elementValue));
            } else {
                roleOptions.setName(elementValue);
            }
            Locator locator = page.getByRole(AriaRole.valueOf(ariaRole.toUpperCase()), roleOptions);
            if (locator.count() > 0) {
                consoleAndHTMLReport.pass("Locate Elements By Role", "Elements Located Successfully.");
                return locator.all();
            } else {
                throw new ElementNotFoundException(String.format("Elements '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Elements By Role", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByText(String pageName, String elementName, Page.GetByTextOptions getByTextOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            String elementValue = elementObj.get("Value").getAsString();
            Locator locator;
            if (isValidPattern(elementValue)) {
                locator = page.getByText(Pattern.compile(elementValue), getByTextOptions);
            } else {
                locator = page.getByText(elementValue, getByTextOptions);
            }
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By Text", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By Text", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Text", e.getMessage(), e);
            throw e;
        }

    }

    public Locator locateElementByText(String pageName, String elementName) throws Exception {
        Page.GetByTextOptions getByTextOptions = new Page.GetByTextOptions();
        return locateElementByText(pageName, elementName, getByTextOptions);
    }

    public Locator locateElementByLabel(String pageName, String elementName, Page.GetByLabelOptions getByLabelOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            Locator locator;
            String elementValue = elementObj.get("Value").getAsString();
            if (isValidPattern(elementValue)) {
                locator = page.getByLabel(Pattern.compile(elementValue), getByLabelOptions);
            } else {
                locator = page.getByLabel(elementValue, getByLabelOptions);
            }
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By Label", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By Label", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Label", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByLabel(String pageName, String elementName) throws Exception {
        Page.GetByLabelOptions getByLabelOptions = new Page.GetByLabelOptions();
        return locateElementByLabel(pageName, elementName, getByLabelOptions);
    }

    public Locator locateElementByPlaceHolder(String pageName, String elementName, Page.GetByPlaceholderOptions placeholderOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            Locator locator;
            String elementValue = elementObj.get("Value").getAsString();
            if (isValidPattern(elementValue)) {
                locator = page.getByPlaceholder(Pattern.compile(elementValue), placeholderOptions);
            } else {
                locator = page.getByPlaceholder(elementValue, placeholderOptions);
            }
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By Placeholder", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By Placeholder", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Placeholder", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByPlaceHolder(String pageName, String elementName) throws Exception {
        Page.GetByPlaceholderOptions getByPlaceholderOptions = new Page.GetByPlaceholderOptions();
        return locateElementByPlaceHolder(pageName, elementName, getByPlaceholderOptions);
    }

    public Locator locateElementByTitle(String pageName, String elementName) throws Exception {
        Page.GetByTitleOptions getByTitleOptions = new Page.GetByTitleOptions();
        return locateElementByTitle(pageName, elementName, getByTitleOptions);
    }

    public Locator locateElementByTitle(String pageName, String elementName, Page.GetByTitleOptions titleOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            Locator locator;
            String elementValue = elementObj.get("Value").getAsString();
            if (isValidPattern(elementValue)) {
                locator = page.getByTitle(Pattern.compile(elementValue), titleOptions);
            } else {
                locator = page.getByTitle(elementValue, titleOptions);
            }
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By Title", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By Title", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Title", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByTestId(String pageName, String elementName) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            Locator locator;
            String elementValue = elementObj.get("Value").getAsString();
            if (isValidPattern(elementValue)) {
                locator = page.getByTestId(Pattern.compile(elementValue));
            } else {
                locator = page.getByTestId(elementValue);
            }
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By Test ID", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By Test ID", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Test ID", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByAltText(String pageName, String elementName, Page.GetByAltTextOptions altTextOptions) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            Locator locator;
            String elementValue = elementObj.get("Value").getAsString();
            if (isValidPattern(elementValue)) {
                locator = page.getByAltText(Pattern.compile(elementValue), altTextOptions);
            } else {
                locator = page.getByAltText(elementValue, altTextOptions);
            }
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By Alt text", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By Alt text", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By Alt text", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByAltText(String pageName, String elementName) throws Exception {
        Page.GetByAltTextOptions altTextOptions = new Page.GetByAltTextOptions();
        return locateElementByAltText(pageName, elementName, altTextOptions);
    }

    public Locator locateElementByCssOrXpath(String pageName, String elementName) throws Exception {
        printCommonLog(pageName, elementName);
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        try {
            Locator locator;
            locator = page.locator(elementObj.get("Value").getAsString());
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By CSS or Xpath", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By CSS or Xpath", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By CSS or Xpath", e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByCssOrXpath(String cssOrXpath) throws ElementNotFoundException {
        consoleAndHTMLReport.info("Element Need To Be Locate");
        consoleAndHTMLReport.info("CSS/XPATH: " + cssOrXpath);
        try {
            Locator locator = page.locator(cssOrXpath);
            if (locator.count() > 0) {
                if (runner.isTakeScreenshotOfEachLocator()) {
                    consoleAndHTMLReport.passWithScreenshot("Locate Element By CSS or Xpath", "Element Located Successfully.", locator.screenshot());
                } else {
                    consoleAndHTMLReport.pass("Locate Element By CSS or Xpath", "Element Located Successfully.");
                }
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element not found. Locator: %s", cssOrXpath));
            }
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Locate Element By CSS or Xpath", e.getMessage(), e);
            throw e;
        }
    }


    public static boolean isValidPattern(String pattern) {
        try {
            Pattern.compile(pattern);
            return true;
        } catch (PatternSyntaxException e) {
            return false;
        }
    }

    JsonObject verifyAndReturnElementFromPageObjects(String pageName, String elementName) throws Exception {
        JsonObject locatorObject = runner.getPageElements();
        JsonObject elementObj;
        try {
            if (locatorObject.has(pageName)) {
                JsonObject pageObject = locatorObject.getAsJsonObject(pageName);
                if (pageObject.has(elementName)) {
                    elementObj = locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
                    if (elementObj.has("Type")) {
                        if (elementObj.get("Type").isJsonNull()) {
                            throw new Exception("Value properties/attribute not found.");
                        }
                        String type = elementObj.get("Type").getAsString();
                        if (type.equalsIgnoreCase("role")) {
                            if (!elementObj.has("AriaRoleType")) {
                                throw new Exception("AriaRoleType properties/attribute not found.");
                            } else {
                                if (elementObj.get("AriaRoleType").isJsonNull())
                                    throw new InvalidPropertiesException("Invalid value is present for property 'AriaRoleType'. Element [" + elementName + "] Details: " + elementObj);
                            }
                        }
                    } else throw new Exception("Type properties/attribute not found.");
                    if (!elementObj.has("Value")) {
                        throw new Exception("Value properties/attribute not found.");
                    } else {
                        if (elementObj.get("Value").isJsonNull())
                            throw new InvalidPropertiesException("Invalid value is present for property 'Value'. Element [" + elementName + "] Details: " + elementObj);
                    }
                } else throw new Exception("Element [%s] not found.".formatted(elementName));
            } else throw new Exception("Page [%s] not found.".formatted(pageName));
        } catch (Exception e) {
            consoleAndHTMLReport.fail("Element Validation", e);
            throw e;
        }
        consoleAndHTMLReport.pass("Element Validation");
        consoleAndHTMLReport.pass(elementObj.toString(), CodeLanguage.JSON);
        return elementObj;
    }


}
