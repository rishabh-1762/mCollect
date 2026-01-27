package baseTest;  // ✅ Fixed package name

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.ConfigReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public abstract class BaseTestClass {
    private ConfigReader config;
    protected static Playwright playwright;
    protected static ThreadLocal<Browser> browserTL = new ThreadLocal<>();
    protected ThreadLocal<BrowserContext> contextTL = new ThreadLocal<>();
    protected ThreadLocal<Page> pageTL = new ThreadLocal<>();

    // ✅ Store screenshot path for ExtentListener
    public static final ThreadLocal<String> lastScreenshotPath = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        config = new ConfigReader();
        playwright = Playwright.create();
        System.out.println("✅ Suite Setup Complete");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        try {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false).setArgs(java.util.Arrays.asList("--start-maximized"))
            );
            browserTL.set(browser);

            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
            contextTL.set(context);

            Page page = context.newPage();
            pageTL.set(page);

            // ✅ Defensive local reference
            this.page = page;


            // ✅ Error listeners
            page.onPageError(error -> System.err.println("Page Error: " + error));
            context.onWebError(webError -> System.err.println("Web Error: " + webError.error()));

            System.out.println("✅ Test Setup Complete");
            page.navigate(config.getProperty("url"));
        } catch (Exception e) {
            System.err.println("Setup failed: " + e.getMessage());
            throw new RuntimeException("Failed to create browser/page", e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Create test instance BEFORE test runs

        // ✅ Defensive screenshot capture
        if (result.getStatus() == ITestResult.FAILURE) {
            lastScreenshotPath.set(captureScreenshot(result.getName()));
        }

        // ✅ Safe cleanup in reverse order
        if (pageTL.get() != null) {
            try { pageTL.get().close(); } catch (Exception ignored) {}
            pageTL.remove();
        }
        if (contextTL.get() != null) {
            try { contextTL.get().close(); } catch (Exception ignored) {}
            contextTL.remove();
        }
        if (browserTL.get() != null) {
            try { browserTL.get().close(); } catch (Exception ignored) {}
            browserTL.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        if (playwright != null) {
            playwright.close();
            System.out.println("✅ Suite Teardown Complete");
        }
    }

    // ✅ Fixed: Added missing field declaration
    protected Page page;

    // ✅ COMPLETE screenshot method with error handling
    private String captureScreenshot(String testName) {
        Page currentPage = pageTL.get();
        if (currentPage == null || currentPage.isClosed()) {
            System.err.println("Page unavailable for screenshot");
            return null;
        }

        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = String.format("test-output/screenshots/%s_%s.png", testName, timestamp);

            Files.createDirectories(Paths.get("test-output/screenshots"));

            // ✅ Wait for stable state
            currentPage.waitForLoadState(LoadState.DOMCONTENTLOADED,
                    new Page.WaitForLoadStateOptions().setTimeout(5000));

            currentPage.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));

            // ✅ Copy to latest ExtentReports folder
            String reportFolder = findLatestReportFolder();
            if (reportFolder != null) {
                String reportScreenshotPath = reportFolder + "/screenshots/" + testName + "_" + timestamp + ".png";
                Files.createDirectories(Paths.get(reportFolder + "/screenshots"));
                Files.copy(Paths.get(screenshotPath), Paths.get(reportScreenshotPath));
                System.out.println("📋 Copied to report: " + reportScreenshotPath);
            }

            System.out.println("📸 Screenshot captured: " + screenshotPath);
            return screenshotPath;
        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
            return null;
        }
    }

    private String findLatestReportFolder() {
        File reportDir = new File("test-output");
        if (!reportDir.exists()) return null;

        File[] sparkFolders = reportDir.listFiles((dir, name) ->
                name.startsWith("SparkReport_") && dir.isDirectory());

        if (sparkFolders != null && sparkFolders.length > 0) {
            Arrays.sort(sparkFolders, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
            return sparkFolders[0].getAbsolutePath();
        }
        return null;
    }
}
