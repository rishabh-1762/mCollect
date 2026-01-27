package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportDir = "test-output/SparkReport_" + timestamp;

            // ✅ CRITICAL: Create screenshots directory alongside report
            createScreenshotsDirectory(reportDir);

            ExtentSparkReporter spark = new ExtentSparkReporter(reportDir + "/index.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Playwright Java Automation Report");
            spark.config().setReportName("Playwright Test Execution");
            //spark.config().setCssClass("screenshot-width"); // Better screenshot display

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Generated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        return extent;
    }

    public static ExtentTest createTest(String name, String description) {
        ExtentReports reports = getExtentReports();
        ExtentTest test = reports.createTest(name, description);
        test.getModel().setStartTime(new Date());
        return test;
    }

    /**
     * ✅ Creates screenshots directory structure matching your console log
     * test-output/SparkReport_20260114_163406/screenshots/
     */
    private static void createScreenshotsDirectory(String reportDir) {
        try {
            // Main report directory
            Path reportPath = Paths.get(reportDir);
            Files.createDirectories(reportPath);

            // Screenshots directory INSIDE report folder
            Path screenshotsPath = reportPath.resolve("screenshots");
            Files.createDirectories(screenshotsPath);

            System.out.println("📁 Created screenshots directory: " + screenshotsPath.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("❌ Failed to create screenshots directory: " + e.getMessage());
        }
    }

    /**
     * ✅ Helper: Copy screenshot to report's screenshots folder for perfect relative paths
     */
    public static String copyScreenshotToReport(String sourceScreenshotPath) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            ExtentReports reports = getExtentReports();

            // Get current report directory
            String reportDir = "test-output/SparkReport_" + timestamp;
            Path targetDir = Paths.get(reportDir, "screenshots");

            String fileName = new File(sourceScreenshotPath).getName();
            Path targetPath = targetDir.resolve(fileName);

            // Copy file
            Files.copy(Paths.get(sourceScreenshotPath), targetPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Return PERFECT relative path for ExtentReports
            return "screenshots/" + fileName;
        } catch (Exception e) {
            System.err.println("❌ Screenshot copy failed: " + e.getMessage());
            return null;
        }
    }
}
