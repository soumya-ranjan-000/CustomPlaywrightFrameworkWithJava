package org.srg.custompw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LocateElementFromPage {
    private Page page;
    private JsonObject locatorObject;

    public LocateElementFromPage(Page page) throws JsonProcessingException {
        this.page = page;
        locatorObject = ResourceHandler.convertYamlToJsonObject("Locators.yml");
    }

    public Locator locateElementByRole(String pageName, String elementName) {
        Page.GetByRoleOptions roleOptions = new Page.GetByRoleOptions();
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        roleOptions = elementObj.has("ExactMatch") ? roleOptions.setExact(elementObj.get("ExactMatch").getAsBoolean()) : roleOptions;
        roleOptions.setExact(elementObj.get("ExactMatch").getAsBoolean());
        return this.locateElementByRole(pageName, elementName, roleOptions);
    }

    public Locator locateElementByRole(String pageName, String elementName, Page.GetByRoleOptions roleOptions) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            roleOptions.setName(Pattern.compile(elementValue));
        } else {
            roleOptions.setName(elementValue);
        }
        return page.getByRole(AriaRole.valueOf(elementObj.get("AriaRoleType").getAsString().toUpperCase()),
                roleOptions);
    }

    public Locator locateElementByText(String pageName, String elementName, Page.GetByTextOptions getByTextOptions) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        String elementValue = elementObj.get("Value").getAsString();
        if (isValidPattern(elementValue)) {
            return page.getByText(Pattern.compile(elementValue), getByTextOptions);
        } else {
            return page.getByText(elementValue, getByTextOptions);
        }
    }

    public Locator locateElementByText(String pageName, String elementName) {
        JsonObject elementObj = this.locatorObject.getAsJsonObject(pageName).getAsJsonObject(elementName);
        Page.GetByTextOptions getByTextOptions = new Page.GetByTextOptions();
        getByTextOptions = elementObj.has("ExactMatch") ? getByTextOptions.setExact(elementObj.get("ExactMatch").getAsBoolean()) : getByTextOptions;
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
}
