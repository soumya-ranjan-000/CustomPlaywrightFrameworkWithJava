package playwritesessions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.srg.custompw.LocateElementFromPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class TestCustomLocatorsClass {
    Page page;
    Setup setup;
    LocateElementFromPage locator = null;

    @BeforeTest
    void openBrowser() throws InterruptedException, JsonProcessingException {
        setup = new Setup();
        page = setup.startBrowser().newPage();
        locator = new LocateElementFromPage(page);
    }

    @AfterTest
    void closeBrowser() throws InterruptedException {
        setup.stopPlaywright();
    }

    @Test
    void testOne() {
        page.navigate("https://login.salesforce.com/");
        Locator username = locator.locateElementByRole("Login Page", "UserName_InputBox");
        Assert.assertNotNull(username);
        username.fill("sales-dev-initial@wipro.com");
        Locator password = locator.locateElementByRole("Login Page", "Password_InputBox");
        Assert.assertNotNull(password);
        password.fill("Admin@760");
        Locator loginBtn = locator.locateElementByRole("Login Page", "Login_button",new Page.GetByRoleOptions().setExact(false));
        Assert.assertNotNull(loginBtn);
        loginBtn.click();
    }

    @Test
    void testTwo() {
        page.navigate("https://login.salesforce.com/");
        Locator username = locator.locateElementByRole("Login Page", "UserName_InputBox");
        Assert.assertNotNull(username);
        username.fill("sales-dev-initial@wipro.com");
        Locator password = locator.locateElementByRole("Login Page", "Password_InputBox");
        Assert.assertNotNull(password);
        password.fill("Admin@760");
        Locator loginBtn = locator.locateElementByRole("Login Page", "Login_button",new Page.GetByRoleOptions().setExact(false));
        Assert.assertNotNull(loginBtn);
        loginBtn.click();
    }
}
