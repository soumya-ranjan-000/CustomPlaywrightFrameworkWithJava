package srg.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import srg.util.ResourceHandler;

import java.io.IOException;

public class ExtentManager {

    public synchronized static ExtentReports createExtentReports(String featureFileName) throws IOException {
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/"+featureFileName+".html");
        reporter.loadXMLConfig(ResourceHandler.getFileFromResourcesFolder("spark-config.xml"));
        reporter.config().setReportName(featureFileName);
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Author", "Soumya Ranjan Ghadei");
        return extentReports;
    }

}
