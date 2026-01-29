package pages.settlement;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class RaiseSettlement {

    private Page page;
    private Locator raiseSettlementRequest;
    private Locator apacNumberInput;
    private Locator okButton;
    private Locator continueWithBothInParallel;

    // Constructor
    public RaiseSettlement(Page page) {
        this.page = page;
        this.raiseSettlementRequest = page.locator("//div[contains(@class,'v-button') and contains(@class,'btn-white')]");
        this.apacNumberInput = page.locator("//input[contains(@class,'v-textfield')]");  // Better locator
        this.okButton = page.locator("//span[@class='v-button-wrap']//span[text()='Ok']");
        this.continueWithBothInParallel = page.getByLabel("Continue with both in parallel");
    }

    public void clickRaiseSettlementButton() {
        try {
            raiseSettlementRequest.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            raiseSettlementRequest.click();
            System.out.println("Clicked Raise Settlement button");
        } catch (Exception e) {
            System.err.println("Failed to click Raise Settlement: " + e.getMessage());
            throw new RuntimeException("Raise Settlement button click failed", e);
        }
    }

    public void enterAPACforSettlementRequest(String APACNumber) {
        try {
            apacNumberInput.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));

            apacNumberInput.clear();
            apacNumberInput.fill(APACNumber);
            System.out.println("Entered APAC Number: " + APACNumber);

            page.waitForTimeout(500);  // Small wait after input
        } catch (Exception e) {
            System.err.println("Failed to enter APAC number: " + e.getMessage());
            throw new RuntimeException("APAC input failed", e);
        }
    }

    public void clickOkButton() {
        try {
            okButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            okButton.click();
            System.out.println("Clicked OK button");
        } catch (Exception e) {
            System.err.println("Failed to click OK: " + e.getMessage());
            throw new RuntimeException("OK button click failed", e);
        }
    }

    public void selectContinueWithBothInParallel() {
        try {
            continueWithBothInParallel.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            continueWithBothInParallel.check();  // For checkboxes/radio buttons
            System.out.println("Selected 'Continue with both in parallel'");
        } catch (Exception e) {
            System.err.println("Failed to select option: " + e.getMessage());
            throw new RuntimeException("Checkbox selection failed", e);
        }
    }

    public boolean isRaiseSettlementPageOpened() {
        try {
            return apacNumberInput.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    // Complete workflow method
    public void raiseSettlementWithAPAC(String APACNumber) {
        clickRaiseSettlementButton();
        enterAPACforSettlementRequest(APACNumber);
        clickOkButton();
    }
}