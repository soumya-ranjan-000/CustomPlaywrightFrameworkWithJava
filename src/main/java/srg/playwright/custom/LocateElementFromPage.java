package srg.playwright.custom;

import com.aventstack.extentreports.Status;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.exceptions.ElementNotFoundException;
import srg.exceptions.InvalidPropertiesException;
import srg.extentreports.ExtentTestLogger;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LocateElementFromPage implements ElementLocator {

    private final Page page;
    TestSetup testRunner = CucumberRunner.testRunner.get();
    ExtentTestLogger testManager = new ExtentTestLogger(testRunner.getExtentLogger());
    Logger logger = LoggerFactory.getLogger(LocateElementFromPage.class);

    public LocateElementFromPage(Page page) {
        this.page = page;
    }

    void printCommonLog(String pageName, String elementName) {
        logger.info(String.format("Element Need To Be Locate: [ Page Name: %s, Element Name: %s ]", pageName, elementName));
        testManager.infoLog(String.format("Element Need To Be Locate: [ Page Name: %s, Element Name: %s ]", pageName, elementName));
    }

    public Locator locateElementByRole(String pageName, String elementName) throws Exception {
        Page.GetByRoleOptions roleOptions = new Page.GetByRoleOptions();
        return locateElementByRole(pageName, elementName, roleOptions);
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
                logger.info("locateElementByRole: Element Located Successfully.");
                testManager.passLog("locateElementByRole", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByRole - FAILED");
            testManager.failLog("locateElementByRole - FAILED");
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
                logger.info("locateElementByText: Element Located Successfully.");
                testManager.passLog("locateElementByText", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByText - FAILED");
            testManager.failLog("locateElementByText - FAILED");
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
                logger.info("locateElementByLabel: Element Located Successfully.");
                testManager.passLog("locateElementByLabel", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByLabel - FAILED");
            testManager.failLog("locateElementByLabel - FAILED");
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
                logger.info("locateElementByPlaceHolder: Element Located Successfully.");
                testManager.passLog("locateElementByPlaceHolder", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByPlaceHolder - FAILED");
            testManager.failLog("locateElementByPlaceHolder - FAILED");
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
                logger.info("locateElementByTitle: Element Located Successfully.");
                testManager.passLog("locateElementByTitle", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByTitle - FAILED");
            testManager.failLog("locateElementByTitle - FAILED");
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
                logger.info("Element Located Successfully.");
                testManager.passLog("Element Located Successfully.");
                logger.info("<=== locateElementByTestId - PASSED ===>");
                testManager.passLog("<=== locateElementByTestId - PASSED ===>");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            logger.error("<=== locateElementByTestId - FAILED ===>");
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            testManager.failLog("<=== locateElementByTestId - FAILED ===>");
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
                logger.info("locateElementByAltText: Element Located Successfully.");
                testManager.passLog("locateElementByAltText", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByAltText - FAILED");
            testManager.failLog("locateElementByAltText - FAILED");
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
                logger.info("locateElementByCssOrXpath: Element Located Successfully.");
                testManager.passLog("locateElementByCssOrXpath", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByCssOrXpath - FAILED");
            testManager.failLog("locateElementByCssOrXpath - FAILED");
            throw e;
        }
    }

    public Locator locateElementByCssOrXpath(String cssOrXpath) throws ElementNotFoundException {
        logger.info("locateElementByCssOrXpath. xpath/css: " + cssOrXpath);
        testManager.infoLog("locateElementByCssOrXpath", "xpath/css: " + cssOrXpath);
        try {
            Locator locator = page.locator(cssOrXpath);
            if (locator.count() > 0) {
                logger.info("locateElementByCssOrXpath: Element Located Successfully.");
                testManager.passLog("locateElementByCssOrXpath", "Element Located Successfully.");
                return locator;
            } else {
                throw new ElementNotFoundException(String.format("Element not found. Locator: %s", cssOrXpath));
            }
        } catch (Exception e) {
            logger.error("Element not found. Reason: {}", e.getMessage(), e);
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            logger.error("locateElementByCssOrXpath - FAILED");
            testManager.failLog("locateElementByCssOrXpath - FAILED");
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
        JsonObject locatorObject = testRunner.getPageElements();
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
            logger.error("Element Validation Failed. Reason: ", e);
            logger.error("Element Verification - FAILED");
            testManager.failLog("Element Validation Failed. Reason", e);
            testManager.failLog("Element Verification - FAILED");
            throw e;
        }
        logger.info("Element Details: {}", elementObj);
        testManager.markUpJSONLog(Status.INFO, elementObj.toString());
        testManager.passLog("Element Verification - PASSED");
        logger.info("Element Verification - PASSED");
        return elementObj;
    }
}
