package utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.HashMap;
import java.util.Map;

public class Filter {
    private Page page;
    private Locator addFilter;
    private Locator searchAutocompleteText;
    private Locator selectBtn;
    private Locator filterInbox;
    private Locator refresh;
    private Locator body;

    public Filter(Page page){
        this.page = page;
        this.addFilter = page.locator("//span[text()='Add search condition']");
        this.searchAutocompleteText = page.getByPlaceholder("Search...");
        this.selectBtn = page.locator("//span[text()='Select']");
        this.filterInbox = page.locator("//input[@class='v-textfield v-widget v-has-width']");
        this.refresh = page.locator("//span[text()='Refresh']");
        this.body = page.locator("//body");
    }


    public void apply(String columnName, String operator, String value) {
        try {
            System.out.println("Applying filter: " + columnName + " " + operator + " " + value);

            // Click Add Filter
            addFilter.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            addFilter.click();

            // Search for column
            searchAutocompleteText.fill(columnName);
            page.waitForTimeout(500);

            // Select column from dropdown
            String columnXpath = "//span[normalize-space(text())='" + columnName + "']";
            Locator columnLocator = page.locator(columnXpath).first();
            columnLocator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            page.keyboard().press("Enter");
            page.waitForTimeout(1000);
            columnLocator.click();

            // Click Select button
            selectBtn.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            selectBtn.click();

            // Enter filter value
            filterInbox.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            filterInbox.fill(value);

            // Click outside to apply
            body.click();
            page.waitForTimeout(2000);  // Wait for filter to apply

            System.out.println("Filter applied successfully");

        } catch (Exception e) {
            System.err.println("Failed to apply filter: " + e.getMessage());
            throw new RuntimeException("Filter application failed", e);
        }
    }

    public void refresh(){
        try {
            refresh.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            refresh.click();
            page.waitForTimeout(2000);
        } catch(Exception e){
            System.err.println("Refresh failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void selectColumnForFilter(Map<String, String> entries){
        try {
            if (entries != null && !entries.isEmpty()) {
                for (Map.Entry<String, String> entry : entries.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    // Use the new apply method
                    apply(key, "contains", value);
                }
            }
        } catch(Exception e){
            System.err.println("Filter selection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}