package srg.playwright.custom;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import srg.CucumberRunner;
import srg.StartTest;
import srg.exceptions.ElementNotFoundException;
import srg.exceptions.InvalidPropertiesException;
import srg.extentreports.ExtentTestLogger;
import srg.util.ResourceHandler;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LocateElementFromPage {
    private StartTest myTestRunner = null;
    private Page page;
    private JsonObject locatorObject;
    private final Logger logger = LoggerFactory.getLogger(LocateElementFromPage.class);
    private ExtentTestLogger testManager = null;

    public LocateElementFromPage(Page page) throws JsonProcessingException {
        this.page = page;
        testManager = new ExtentTestLogger(CucumberRunner.testRunner.get().getExtentLogger());
        locatorObject = ResourceHandler.convertYamlToJsonObject("PageObjects.yml");
    }

    public Locator locateElementByRole(String pageName, String elementName) throws Exception {
        Page.GetByRoleOptions roleOptions = new Page.GetByRoleOptions();
        return locateElementByRole(pageName, elementName, roleOptions);
    }

    public Locator locateElementByRole(String pageName, String elementName, Page.GetByRoleOptions roleOptions) throws Exception {
        try {
            JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
            logger.debug(String.format("Locating Element: {Page Name: %s, Element Name: %s}", pageName, elementName));
            logger.info("Element details: {}", elementObj.toString());
            testManager.infoLog(String.format("<b>Locating Element:</b> <i>{Page Name: %s, Element Name: %s}</i>", pageName, elementName));
            testManager.markUpJSONLog(Status.INFO, elementObj.toString());
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
                logger.debug(String.format("Successfully Located Element. Details: %s", elementObj));
                testManager.passLog("Element Located Successfully.");
                return locator;
            } else {
                logger.error(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
                throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            }
        } catch (Exception e) {
            testManager.failLog("<p style=\"color: red; font-weight: bold;\">" + "Element not found." + "</p>" +
                    "<b>Reason:</b> " + e.getMessage(), e);
            throw e;
        }
    }

    public Locator locateElementByText(String pageName, String elementName, Page.GetByTextOptions getByTextOptions) throws Exception {
        JsonObject elementObj = this.verifyAndReturnElementFromPageObjects(pageName, elementName);
        logger.debug(String.format("Locating Element: {Page Name: %s, Element Name: %s}", pageName, elementName));
        logger.info("Element details: {}", elementObj.toString());
        testManager.infoLog(String.format("<b>Locating Element:</b> <i>{Page Name: %s, Element Name: %s}</i>", pageName, elementName));
        testManager.markUpJSONLog(Status.INFO, elementObj.toString());
        String elementValue = elementObj.get("Value").getAsString();
        Locator locator = null;
        if (isValidPattern(elementValue)) {
            locator = page.getByText(Pattern.compile(elementValue), getByTextOptions);
        } else {
            locator = page.getByText(elementValue, getByTextOptions);
        }
        if (locator.count() > 0) {
            logger.debug(String.format("Successfully Located Element. Details: %s", elementObj));
            testManager.passLog("Element Located Successfully.");
            return locator;
        } else {
            logger.error(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
            testManager.failLog("Element not found!!!");
            throw new ElementNotFoundException(String.format("Element '%s' not found. Details: %s", elementName, elementObj));
        }
    }

    public Locator locateElementByText(String pageName, String elementName) throws Exception {
        Page.GetByTextOptions getByTextOptions = new Page.GetByTextOptions();
        return locateElementByText(pageName, elementName, getByTextOptions);
    }

    public Locator locateElementByLabel(String pageName, String elementName, Page.GetByLabelOptions getByLabelOptions) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            return page.getByLabel(Pattern.compile(elementValue), getByLabelOptions);
        } else {
            return page.getByLabel(elementValue, getByLabelOptions);
        }
    }

    public Locator locateElementByLabel(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        Page.GetByLabelOptions getByLabelOptions = new Page.GetByLabelOptions();
        getByLabelOptions = elementObj.has("ExactMatch") ? getByLabelOptions.setExact(elementObj.get("ExactMatch").getAsBoolean()) : getByLabelOptions;
        return locateElementByLabel(pageName, elementName, getByLabelOptions);
    }

    public Locator locateElementByPlaceHolder(String pageName, String elementName, Page.GetByPlaceholderOptions placeholderOptions) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            return page.getByPlaceholder(Pattern.compile(elementValue), placeholderOptions);
        } else {
            return page.getByPlaceholder(elementValue, placeholderOptions);
        }
    }

    public Locator locateElementByPlaceHolder(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        Page.GetByPlaceholderOptions getByPlaceholderOptions = new Page.GetByPlaceholderOptions();
        getByPlaceholderOptions = elementObj.has("ExactMatch") ? getByPlaceholderOptions.setExact(elementObj.get("ExactMatch").getAsBoolean()) : getByPlaceholderOptions;
        return locateElementByPlaceHolder(pageName, elementName, getByPlaceholderOptions);
    }

    public Locator locateElementByTitle(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        Page.GetByTitleOptions getByTitleOptions = new Page.GetByTitleOptions();
        getByTitleOptions = elementObj.has("ExactMatch") ? getByTitleOptions.setExact(elementObj.get("ExactMatch").getAsBoolean()) :
                getByTitleOptions;
        return locateElementByTitle(pageName, elementName, getByTitleOptions);
    }

    public Locator locateElementByTitle(String pageName, String elementName, Page.GetByTitleOptions titleOptions) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            return page.getByTitle(Pattern.compile(elementValue), titleOptions);
        } else {
            return page.getByTitle(elementValue, titleOptions);
        }
    }

    public Locator locateElementByTestId(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            return page.getByTestId(Pattern.compile(elementValue));
        } else {
            return page.getByTestId(elementValue);
        }
    }

    public Locator locateElementByAltText(String pageName, String elementName, Page.GetByAltTextOptions altTextOptions) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            return page.getByAltText(Pattern.compile(elementValue), altTextOptions);
        } else {
            return page.getByAltText(elementValue, altTextOptions);
        }
    }

    public Locator locateElementByAltText(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        Page.GetByAltTextOptions altTextOptions = new Page.GetByAltTextOptions();
        altTextOptions = elementObj.has("ExactMatch") ? altTextOptions.setExact(elementObj.get("ExactMatch").getAsBoolean()) :
                altTextOptions;
        return locateElementByAltText(pageName, elementName, altTextOptions);
    }

    public Locator locateElementByCssOrXpath(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        return page.locator(elementObj.get("Value").getAsString());
    }

    public Locator locateElementByCssOrXpath(String cssOrXpath) {
        return page.locator(cssOrXpath);
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
        JsonObject elementObj = null;
        try {
            if (this.locatorObject.has(pageName)) {
                JsonObject pageObject = this.locatorObject.getAsJsonObject(pageName);
                if (pageObject.has(elementName)) {
                    elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
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
            testManager.failLog("Step Failed: ", e);
            throw e;
        }
        return elementObj;
    }
}
