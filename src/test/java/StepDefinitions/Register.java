package StepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import srg.CucumberRunner;
import srg.TestSetup;
import srg.exceptions.ElementNotFoundException;
import srg.playwright.custom.CommonOperation;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

public class Register {
    TestSetup myTestRunner = CucumberRunner.testRunner.get();
    CommonOperation uiOperation = new CommonOperation(myTestRunner.getPlaywrightFactory().getPage());

    public static String generateRandomString(int byteLength) {
        // Create a SecureRandom instance
        SecureRandom secureRandom = new SecureRandom();

        // Generate random bytes
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);

        // Encode the bytes to a Base64 string
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Given("Open the Application")
    public void open_the_application() {
        uiOperation.navigateToUrl("https://tutorialsninja.com/demo/");
    }

    @When("Click on {string} Drop menu")
    public void click_on_drop_menu(String menuItem) throws Exception {
        switch (menuItem) {
            case "My Account":
                Locator myAccount = uiOperation.
                        locateElementByCssOrXpath("Home Page", "My_Account_Drop_Menu");
                uiOperation.clickOnElement(myAccount);
                break;
        }
    }

    @Then("Click on {string} option")
    public void click_on_option(String optionName) throws Exception {
        uiOperation.
                locateElementByRole("Home Page", "Register").
                click();
    }

    @Then("Enter Account Details")
    public void enter_account_details(@Transpose Map<String, String> accountDetails) throws Exception {
        uiOperation.getPage().waitForLoadState(LoadState.LOAD);
        uiOperation.locateElementByRole("Register Page", "FirstNameTextBox").
                fill(accountDetails.get("First Name") + generateRandomString(5));
        uiOperation.locateElementByRole("Register Page", "LastNameTextBox").
                fill(accountDetails.get("Last Name") + generateRandomString(5));
        uiOperation.locateElementByRole("Register Page", "EmailTextBox").
                fill(generateRandomString(5) + accountDetails.get("E-Mail"));
        uiOperation.locateElementByRole("Register Page", "TelephoneTextBox").
                fill(accountDetails.get("Telephone"));
        String password = generateRandomString(10);
        uiOperation.locateElementByCssOrXpath("Register Page", "PasswordTextBox").
                fill(password);
        uiOperation.locateElementByRole("Register Page", "PasswordConfirmTextBox").
                fill(password);
        uiOperation.clickOnElement(uiOperation.
                locateElementByCssOrXpath("Register Page", "PrivacyPolicyAgreeCheckBox"));
    }

    @Then("Click on {string} button displayed in {string} page")
    public void click_on_button_displayed_in_page(String buttonName, String pageName) throws Exception {
        switch (pageName) {
            case "Register":
                uiOperation.clickOnElement(uiOperation.
                        locateElementByRole("Register Page", "ContinueButton"));
                break;
            case "Account Success":
                uiOperation.clickOnElement(uiOperation.
                        locateElementByRole("Account_Success_Page", "ContinueButton"));
                break;
        }
    }

    @Then("Verify User should be logged in and taken to Account Success page")
    public void verify_user_should_be_logged_in_and_taken_to_page() throws Exception {
        Assert.assertEquals(uiOperation.locateElementByRole("Account_Success_Page", "AccountCreatedHeader").isVisible(), true);
    }

    @Then("Verify proper details should be displayed on the {string} page")
    public void verify_proper_details_should_be_displayed_on_the_page(String string) throws ElementNotFoundException {
        Assert.assertEquals(uiOperation.locateElementByCssOrXpath("//h1[text()='Your Account Has Been Created!']/parent::div/p[1]").
                textContent(), "Congratulations! Your new account has been successfully created!");
        Assert.assertEquals(uiOperation.locateElementByCssOrXpath("//h1[text()='Your Account Has Been Created!']/parent::div/p[2]").
                textContent(), "You can now take advantage of member privileges to enhance your online shopping experience with us.");
        Assert.assertEquals(uiOperation.locateElementByCssOrXpath("//h1[text()='Your Account Has Been Created!']/parent::div/p[3]").
                textContent(), "If you have ANY questions about the operation of this online shop, please e-mail the store owner.");
        Assert.assertEquals(uiOperation.locateElementByCssOrXpath("//h1[text()='Your Account Has Been Created!']/parent::div/p[4]").
                textContent(), "A confirmation has been sent to the provided e-mail address. If you have not received it within the hour, please contact us.");
    }

    @Then("Verify User should be taken to {string} page")
    public void verify_user_should_be_taken_to_page(String pageName) throws Exception {
        switch (pageName) {
            case "Account":
                Assert.assertEquals(uiOperation.locateElementByCssOrXpath("Account_Page", "MyAccountHeader").isVisible(), true);
                break;
        }
    }

    void printHelloWorld(){
        System.out.println("Hello World");
    }
}
