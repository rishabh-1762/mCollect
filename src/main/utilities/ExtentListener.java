package utilities;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ExtentManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ExtentListener implements ITestListener {
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🔥 onTestStart: " + result.getMethod().getMethodName());
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

        ExtentTest test = ExtentManager.createTest(
                result.getMethod().getMethodName() + " [" + timestamp + "]",
                result.getMethod().getDescription() != null ? result.getMethod().getDescription() : ""
        );
        test.info("Test execution started ✅");
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ onTestSuccess: " + result.getMethod().getMethodName());
        ExtentTest test = extentTest.get();
        if (test != null) {
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            test.pass(MarkupHelper.createLabel("✓ Test Passed at " + timestamp, ExtentColor.GREEN));
            test.getModel().setEndTime(new Date());
        }
        extentTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("💥 onTestFailure: " + result.getMethod().getMethodName());
        ExtentTest test = extentTest.get();

        if (test != null) {
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            test.fail("✗ Test Failed at " + timestamp);

            // ✅ PERFECTED: Use ExtentManager's copy method + fallback
            String latestScreenshot = findLatestScreenshot(result.getName());
            if (latestScreenshot != null) {
                String relativePath = ExtentManager.copyScreenshotToReport(latestScreenshot);
                if (relativePath != null) {
                    try {
                        test.addScreenCaptureFromPath(relativePath, "Failure Screenshot");
                        System.out.println("📋 Copied to report: " + relativePath);
                    } catch (Exception e) {
                        //test.error("Screenshot display failed: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("❌ No screenshot found for: " + result.getName());
            }

            // Add error message with proper formatting
            String errorMsg = result.getThrowable() != null ?
                    result.getThrowable().getMessage() : "Unknown error";
            test.fail(MarkupHelper.createLabel("Error: " + errorMsg, ExtentColor.RED));
            test.getModel().setEndTime(new Date());
        } else {
            System.err.println("❌ ExtentTest instance is NULL - check ExtentManager.createTest()");
        }
        extentTest.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            String reason = result.getThrowable() != null ?
                    result.getThrowable().getMessage() : "No reason provided";
            test.skip("Test Skipped: " + reason);
            test.getModel().setEndTime(new Date());
        }
        extentTest.remove();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.warning("Test passed within success percentage");
            test.getModel().setEndTime(new Date());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🚀 Suite START");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Suite FINISH - Flushing report");
        ExtentManager.getExtentReports().flush();
        System.out.println("✅ Report flushed!");
        extentTest.remove();
    }

    private String findLatestScreenshot(String testName) {
        File dir = new File("test-output/screenshots");
        System.out.println("🔍 Searching in: " + dir.getAbsolutePath());

        if (!dir.exists()) {
            System.out.println("❌ Screenshot dir NOT found!");
            return null;
        }

        File[] files = dir.listFiles((d, name) ->
                name.startsWith(testName + "_") && name.endsWith(".png"));
        System.out.println("🔍 Found " + (files != null ? files.length : 0) + " files for " + testName);

        if (files != null && files.length > 0) {
            Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
            String latestPath = files[0].getAbsolutePath();
            System.out.println("✅ Latest screenshot: " + latestPath);
            return latestPath;
        }
        return null;
    }
}
