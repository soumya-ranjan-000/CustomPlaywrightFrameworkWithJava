package srg.playwright.custom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import srg.exceptions.ElementNotFoundException;

public class LocateElementFromFrame implements ElementLocator {
    @Override
    public Locator locateElementByRole(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByRole(String pageName, String elementName, Page.GetByRoleOptions roleOptions) throws Exception {
        return ElementLocator.super.locateElementByRole(pageName, elementName, roleOptions);
    }

    @Override
    public Locator locateElementByText(String pageName, String elementName, Page.GetByTextOptions getByTextOptions) throws Exception {
        return ElementLocator.super.locateElementByText(pageName, elementName, getByTextOptions);
    }

    @Override
    public Locator locateElementByText(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByLabel(String pageName, String elementName, Page.GetByLabelOptions getByLabelOptions) throws Exception {
        return ElementLocator.super.locateElementByLabel(pageName, elementName, getByLabelOptions);
    }

    @Override
    public Locator locateElementByLabel(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByPlaceHolder(String pageName, String elementName, Page.GetByPlaceholderOptions placeholderOptions) throws Exception {
        return ElementLocator.super.locateElementByPlaceHolder(pageName, elementName, placeholderOptions);
    }

    @Override
    public Locator locateElementByTitle(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByTitle(String pageName, String elementName, Page.GetByTitleOptions titleOptions) throws Exception {
        return ElementLocator.super.locateElementByTitle(pageName, elementName, titleOptions);
    }

    @Override
    public Locator locateElementByTestId(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByAltText(String pageName, String elementName, Page.GetByAltTextOptions altTextOptions) throws Exception {
        return ElementLocator.super.locateElementByAltText(pageName, elementName, altTextOptions);
    }

    @Override
    public Locator locateElementByAltText(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByCssOrXpath(String pageName, String elementName) throws Exception {
        return null;
    }

    @Override
    public Locator locateElementByCssOrXpath(String cssOrXpath) throws ElementNotFoundException {
        return null;
    }
}
