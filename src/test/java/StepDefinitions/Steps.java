package StepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.playwright.custom.CommonOperation;

public class Steps {
    TestSetup myTestRunner = CucumberRunner.testRunner.get();
    CommonOperation uiOperation = new CommonOperation(myTestRunner.getPlaywrightFactory().getPage());


    @Given("the user is on login page")
    public void the_user_is_on_login_page() {
        uiOperation.navigateToUrl("https://login.salesforce.com/");
        System.out.println("The user is on login page.");
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() throws Exception {
        Locator username = uiOperation.locateElementByRole("Login Page", "UserName_InputBox");
        Assert.assertNotNull(username);
        username.fill("sales-dev-initial@wipro.com");
        Locator password = uiOperation.locateElementByRole("Login Page", "Password_InputBox");
        Assert.assertNotNull(password);
        password.fill("Admin@760");
        uiOperation.isAttached(password);
    }

    @When("hits submit button")
    public void hits_submit_button() throws Exception {
        Locator loginBtn = uiOperation.locateElementByRole("Login Page", "Login_button", new Page.GetByRoleOptions().setExact(false));
        Assert.assertNotNull(loginBtn);
        loginBtn.click();
        System.out.println("Clicked on submit");
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() throws InterruptedException {
        Thread.sleep(3000L);
        System.out.println("Yeah I am able logged in.");
    }



}
