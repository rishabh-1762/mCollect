package pages.settlement;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class raiseSettlementTab {

    private Page page;
    private Locator settlementTypeDropdown;
    private Locator nextButton;
    private Locator saveAsDraftButton;
    private Locator previousButton;
    private Locator closeButton;
    private Locator facilityDetailsDropdown;
    private Locator securedUnsecuredDropdown;
    private Locator physicalProssessionStatusRadioButton;
    private Locator reasonOfDefault;


    //constructor
    public raiseSettlementTab(Page page){
        this.page = page;

        this.settlementTypeDropdown = page.locator("//div[@class='v-filterselect-button']");
        this.nextButton = page.locator("//div[contains(@class,'v-button-next-button')]");
        this.saveAsDraftButton = page.locator("//div[contains(@class,'v-slot-save-draft-button')]//div[@role='button']");
        this.previousButton = page.locator("//div[contains(@class,'v-button') and contains(@class,'previous-button')]");
        this.closeButton = page.locator("//div[contains(@class,'v-window-closebox')]");
        this.facilityDetailsDropdown = page.locator("//div[contains(@class,'settlement-fields')]//div[@role='combobox'][1]");
        this.securedUnsecuredDropdown = page.locator("//div[contains(@class,'settlement-fields')]//div[@role='combobox'][2]");
        this.physicalProssessionStatusRadioButton = page.locator("//span[@class='v-select-option']");
        this.reasonOfDefault = page.locator("//");

    }
    public void selectSettlementType(String type) {
        try {
            settlementTypeDropdown.click();
            page.waitForTimeout(500);

            // Select option from dropdown
            String optionXpath = "//span[text()='" + type + "']";
            page.locator(optionXpath).click();

            System.out.println("Selected settlement type: " + type);
        } catch (Exception e) {
            System.err.println("Failed to select settlement type: " + e.getMessage());
            throw new RuntimeException("Settlement type selection failed", e);
        }
    }

    // Method to enter Collection Location
    public void enterCollectionLocation(String location) {
        try {
            collectionLocationInput.clear();
            collectionLocationInput.fill(location);
            System.out.println("Entered collection location: " + location);
        } catch (Exception e) {
            System.err.println("Failed to enter collection location: " + e.getMessage());
            throw new RuntimeException("Collection location input failed", e);
        }
    }

    // Method to click Next
    public void clickNext() {
        try {
            nextButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            nextButton.click();
            page.waitForTimeout(1000);
            System.out.println("Clicked Next button");
        } catch (Exception e) {
            System.err.println("Failed to click Next: " + e.getMessage());
            throw new RuntimeException("Next button click failed", e);
        }
    }

    // Method to click Save as Draft
    public void clickSaveAsDraft() {
        try {
            saveAsDraftButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            saveAsDraftButton.click();
            page.waitForTimeout(1000);
            System.out.println("Clicked Save as Draft button");
        } catch (Exception e) {
            System.err.println("Failed to click Save as Draft: " + e.getMessage());
            throw new RuntimeException("Save as Draft button click failed", e);
        }
    }

    // Method to click Previous
    public void clickPrevious() {
        try {
            previousButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            previousButton.click();
            page.waitForTimeout(1000);
            System.out.println("Clicked Previous button");
        } catch (Exception e) {
            System.err.println("Failed to click Previous: " + e.getMessage());
            throw new RuntimeException("Previous button click failed", e);
        }
    }

    // Check if Next button is enabled
    public boolean isNextButtonEnabled() {
        try {
            String classes = nextButton.getAttribute("class");
            return !classes.contains("v-disabled");
        } catch (Exception e) {
            return false;
        }
    }

    // Check if page is opened
    public boolean isRaiseSettlementRequestPageOpened() {
        try {
            return page.locator("//div[text()='Raise Settlement Request']").isVisible();
        } catch (Exception e) {
            return false;
        }
    }
    // Method to select from Facility Details dropdown
    public void selectFacilityDetails(String facilityType) {
        try {
            // Click the dropdown button
            Locator dropdownButton = page.locator("//div[text()='Facility Details']/following-sibling::div//div[@class='v-filterselect-button']");
            dropdownButton.click();
            page.waitForTimeout(500);

            // Wait for dropdown popup
            page.locator("//div[contains(@class,'v-filterselect-suggestpopup')]").waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));

            // Select option from dropdown
            String optionXpath = "//div[contains(@class,'v-filterselect-suggestpopup')]//td//span[text()='" + facilityType + "']";
            page.locator(optionXpath).click();

            System.out.println("Selected Facility Details: " + facilityType);
        } catch (Exception e) {
            System.err.println("Failed to select Facility Details: " + e.getMessage());
            throw new RuntimeException("Facility Details selection failed", e);
        }
    }
    // Method to select from Secured/Unsecured dropdown
    public void selectSecuredUnsecured(String type) {
        try {
            // Click the dropdown button
            Locator dropdownButton = page.locator("//div[text()='Secured/Unsecured Facility']/following-sibling::div//div[@class='v-filterselect-button']");
            dropdownButton.click();
            page.waitForTimeout(500);

            // Wait for dropdown popup
            page.locator("//div[contains(@class,'v-filterselect-suggestpopup')]").waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));

            // Select option
            String optionXpath = "//div[contains(@class,'v-filterselect-suggestpopup')]//td//span[text()='" + type + "']";
            page.locator(optionXpath).click();

            System.out.println("Selected Secured/Unsecured: " + type);
        } catch (Exception e) {
            System.err.println("Failed to select Secured/Unsecured: " + e.getMessage());
            throw new RuntimeException("Secured/Unsecured selection failed", e);
        }
    }
    // Method to get all available options from Facility Details dropdown
    public void printFacilityDetailsOptions() {
        try {
            // Click dropdown to open
            Locator dropdownButton = page.locator("//div[text()='Facility Details']/following-sibling::div//div[@class='v-filterselect-button']");
            dropdownButton.click();
            page.waitForTimeout(1000);

            // Get all options
            System.out.println("\n=== Facility Details Options ===");
            page.locator("//div[contains(@class,'v-filterselect-suggestpopup')]//td//span").all().forEach(option -> {
                String text = option.textContent().trim();
                if (!text.isEmpty()) {
                    System.out.println("  - " + text);
                }
            });

            // Close dropdown by pressing Escape
            page.keyboard().press("Escape");

        } catch (Exception e) {
            System.err.println("Failed to get options: " + e.getMessage());
        }
    }



}
