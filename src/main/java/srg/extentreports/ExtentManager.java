package srg.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    public synchronized static ExtentReports createExtentReports(String featureFileName) {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/"+featureFileName+".html");
        reporter.config().setReportName(featureFileName);
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Author", "Soumya Ranjan Ghadei");
        return extentReports;
    }

}
