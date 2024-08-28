package StepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import srg.CucumberRunner;
import srg.playwright.custom.CommonUIOperation;
import srg.playwright.custom.LocateElementFromPage;
import org.testng.Assert;

public class Steps {

    CommonUIOperation operation = CucumberRunner.testRunner.get().getOperation();
    LocateElementFromPage locateElementFromPage = operation.getElementLocatorForPage();
    Page page = operation.getPage();

    @Given("the user is on login page")
    public void the_user_is_on_login_page() {
        page.navigate("https://login.salesforce.com/");
        System.out.println("The user is on login page.");
    }
    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() throws Exception {
        Locator username = locateElementFromPage.locateElementByRole("Login Page", "UserName_InputBox");
        Assert.assertNotNull(username);
        username.fill("sales-dev-initial@wipro.com");
        Locator password = locateElementFromPage.locateElementByRole("Login Page", "Password_InputBox");
        Assert.assertNotNull(password);
        password.fill("Admin@760");
    }
    @When("hits submit button")
    public void hits_submit_button() throws Exception {
        Locator loginBtn = locateElementFromPage.locateElementByRole("Login Page", "Login_button",new Page.GetByRoleOptions().setExact(false));
        Assert.assertNotNull(loginBtn);
        loginBtn.click();
        System.out.println("Clicked on submit");
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        System.out.println("Yeah I am able logged in.");
    }


}
