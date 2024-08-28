package playwriteconcepts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class PWActions {

    Page page;
    PlaywrightSetup playwrightSetup;

    @BeforeTest
    void openBrowser() throws InterruptedException {
        playwrightSetup = new PlaywrightSetup();
        page = playwrightSetup.getNewBrowserContextFromNewBrowser().newPage();
    }

    @AfterTest
    void closeBrowser() throws InterruptedException {
        playwrightSetup.stopPlaywright();
    }

    @Test
    void textInputTest() {
        page.navigate("https://ecommerce-playground.lambdatest.io/");
        Locator searchBox = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("search"));
        searchBox.fill("random text entered");
        searchBox.clear();
        searchBox.fill("Palm Treo Pro");
        page.getByText("Search").click();
        System.out.println(page.url());
    }

    @Test
    void checkBoxesAndRadioButtons(){
        page.navigate("https://testautomationpractice.blogspot.com/");
        Locator sundayCheckbox=page.getByLabel("Sunday");
        System.out.println("sundayCheckbox.isChecked(): "+ sundayCheckbox.isChecked());
        sundayCheckbox.setChecked(true);
        System.out.println("sundayCheckbox.isChecked(): "+ sundayCheckbox.isChecked());
        sundayCheckbox.click();
        System.out.println("sundayCheckbox.isChecked(): "+ sundayCheckbox.isChecked());
        sundayCheckbox.check();
        sundayCheckbox.uncheck();
        sundayCheckbox.check(new Locator.CheckOptions().setForce(true));
        Locator maleRadioBtn=page.getByLabel("Male");
        Locator femaleRadioBtn=page.getByLabel("Female");
        System.out.println("maleRadioBtn.isChecked(): "+maleRadioBtn.isChecked());
        System.out.println("femaleRadioBtn.isChecked(): "+femaleRadioBtn.isChecked());
        maleRadioBtn.check();
        System.out.println("maleRadioBtn.isChecked(): "+maleRadioBtn.isChecked());
        System.out.println("femaleRadioBtn.isChecked(): "+femaleRadioBtn.isChecked());
        femaleRadioBtn.check();
        System.out.println("maleRadioBtn.isChecked(): "+maleRadioBtn.isChecked());
        System.out.println("femaleRadioBtn.isChecked(): "+femaleRadioBtn.isChecked());
    }

    @Test
    void mouseClicks() throws InterruptedException {
        page.navigate("https://login.salesforce.com/");
        page.fill("input[type=email]","sales-dev-initial@wipro.com");
        page.fill("input[type=password]","Admin@760");
        page.click("input[name=Login]");
        page.waitForURL("https://d5j000005js8qeag-dev-ed.lightning.force.com/lightning/setup/SetupOneHome/home");
        page.waitForLoadState(LoadState.NETWORKIDLE,new Page.WaitForLoadStateOptions().setTimeout(0));
        page.locator("//span[text()='Object Manager']").dblclick();
        page.locator("//input[@title='Search Setup']").pressSequentially("Hello World");
    }
}
