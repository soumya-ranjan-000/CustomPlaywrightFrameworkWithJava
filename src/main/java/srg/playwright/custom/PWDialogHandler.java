package srg.playwright.custom;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import srg.extentreports.LogToConsoleAndHTMLReport;

public class PWDialogHandler {
    private static String DIALOG_MESSAGE = null;
    private static String DIALOG_DEFAULT_VALUE = null;
    private static String DIALOG_TYPE = null;

    LogToConsoleAndHTMLReport consoleAndHTMLReport = new LogToConsoleAndHTMLReport();

    public void accept(Locator locator, Page page) {
        page.onDialog(dialog -> {
            setDialogProperties(dialog);
            dialog.accept();
        });
        locator.click();
        consoleAndHTMLReport.pass("Dialog Accepted.");
    }

    public void accept(Locator locator, Page page, String promptText) {
        page.onDialog(dialog -> {
            setDialogProperties(dialog);
            dialog.accept(promptText);
        });
        locator.click();
        consoleAndHTMLReport.pass("Dialog Accepted.", "Prompt Text: " + promptText);
    }

    public void dismiss(Locator locator, Page page) {
        page.onDialog(dialog -> {
            setDialogProperties(dialog);
            dialog.dismiss();
        });
        locator.click();
        consoleAndHTMLReport.pass("Dialog Dismissed.");
    }

    void setDialogProperties(Dialog dialog) {
        DIALOG_MESSAGE = dialog.message();
        DIALOG_DEFAULT_VALUE = dialog.defaultValue();
        DIALOG_TYPE = dialog.type();
    }
}
