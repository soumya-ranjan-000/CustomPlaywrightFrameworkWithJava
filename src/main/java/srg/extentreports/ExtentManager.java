package srg.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;

public class ExtentManager {

    public synchronized static ExtentReports createExtentReports(String featureFileName) throws IOException {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/"+featureFileName+".html");
//        reporter.loadXMLConfig(ResourceHandler.getFileFromResourcesFolder("spark-config.xml"));
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setReportName(featureFileName);
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Author", "Soumya Ranjan Ghadei");
        return extentReports;
    }

}
