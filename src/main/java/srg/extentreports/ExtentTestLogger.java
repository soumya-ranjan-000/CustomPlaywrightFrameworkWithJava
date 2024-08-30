package srg.extentreports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentTestLogger {
    ExtentTest extentTestLogger;

    public ExtentTestLogger(ExtentTest logger) {
        this.extentTestLogger = logger;
    }

    public ExtentTest getLogger(){
        return this.extentTestLogger;
    }

    public void infoLog(String stepName, String details) {
        extentTestLogger.log(Status.INFO, stepName + " [" + details + "]");
    }

    public void infoLog(String details) {
        extentTestLogger.log(Status.INFO, details);
    }

    public void warnLog(String details) {
        extentTestLogger.log(Status.WARNING, details);
    }

    public void warnLog(String stepName, String details) {
        extentTestLogger.log(Status.WARNING, stepName + " [" + details + "]");
    }

    public void failLog(String message, Throwable e) {
        extentTestLogger.log(Status.FAIL, message);
        extentTestLogger.log(Status.FAIL, e);
    }

    public void failLog(String message) {
        extentTestLogger.log(Status.FAIL, message);
    }

    public void passLog(String stepName, String details) {
        extentTestLogger.log(Status.PASS, stepName + " [" + details + "]");
    }

    public void passLog(String message) {
        extentTestLogger.log(Status.PASS, message);
    }

    public void markUpJSONLog(Status status, String codeBlock){
        Markup m = MarkupHelper.createCodeBlock(codeBlock, CodeLanguage.JSON);
        extentTestLogger.log(status,m);
    }
    public void markUpXMLLog(Status status, String codeBlock){
        Markup m = MarkupHelper.createCodeBlock(codeBlock, CodeLanguage.XML);
        extentTestLogger.log(status,m);
    }

}
