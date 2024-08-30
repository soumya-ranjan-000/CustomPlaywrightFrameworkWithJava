package srg.playwright.custom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import srg.exceptions.ElementNotFoundException;

public interface ElementLocator {

    Locator locateElementByRole(String pageName, String elementName) throws Exception;

    default Locator locateElementByRole(String pageName, String elementName, Page.GetByRoleOptions roleOptions) throws Exception {
        return null;
    }

    default Locator locateElementByText(String pageName, String elementName, Page.GetByTextOptions getByTextOptions) throws Exception {
        return null;
    }

    Locator locateElementByText(String pageName, String elementName) throws Exception;

    default Locator locateElementByLabel(String pageName, String elementName, Page.GetByLabelOptions getByLabelOptions) throws Exception {
        return null;
    }

    Locator locateElementByLabel(String pageName, String elementName) throws Exception;

    default Locator locateElementByPlaceHolder(String pageName, String elementName, Page.GetByPlaceholderOptions placeholderOptions) throws Exception {
        return null;
    }

    Locator locateElementByTitle(String pageName, String elementName) throws Exception;

    default Locator locateElementByTitle(String pageName, String elementName, Page.GetByTitleOptions titleOptions) throws Exception {
        return null;
    }

    Locator locateElementByTestId(String pageName, String elementName) throws Exception;

    default Locator locateElementByAltText(String pageName, String elementName, Page.GetByAltTextOptions altTextOptions) throws Exception {
        return null;
    }

    Locator locateElementByAltText(String pageName, String elementName) throws Exception;

    Locator locateElementByCssOrXpath(String pageName, String elementName) throws Exception;

    Locator locateElementByCssOrXpath(String cssOrXpath) throws ElementNotFoundException;
}
