package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CaseMaster {
    private Page page;

    // Locators
    private String apacColumnSelector = "table tbody tr td:nth-child(9)";
    private Locator apacColumn;

    // Constructor (no return type, not even void)
    public CaseMaster(Page page) {
        this.page = page;
        this.apacColumn = page.locator(apacColumnSelector);
    }

    public String getFirstApacNumber() {
        try {
            // Wait for table to load
            apacColumn.first().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));

            String apacNumber = apacColumn.first().textContent().trim();
            System.out.println("Found APAC Number: " + apacNumber);
            return apacNumber;

        } catch (Exception e) {
            System.err.println("Failed to get APAC number: " + e.getMessage());
            throw new RuntimeException("Could not find APAC number", e);
        }
    }

    // Additional useful methods
    public int getApacRecordCount() {
        try {
            return apacColumn.count();
        } catch (Exception e) {
            System.err.println("Failed to count APAC records: " + e.getMessage());
            return 0;
        }
    }

    public String getApacNumberByIndex(int index) {
        try {
            apacColumn.nth(index).waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            return apacColumn.nth(index).textContent().trim();
        } catch (Exception e) {
            System.err.println("Failed to get APAC number at index " + index);
            return null;
        }
    }

    public boolean isApacNumberPresent(String apacNumber) {
        try {
            String xpath = "//table//tbody//tr//td[normalize-space(text())='" + apacNumber + "']";
            return page.locator(xpath).count() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForTableToLoad() {
        try {
            page.waitForSelector("table tbody tr", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            System.out.println("Cases Master table loaded");
        } catch (Exception e) {
            System.err.println("Table did not load: " + e.getMessage());
            throw new RuntimeException("Cases Master table failed to load", e);
        }
    }
}