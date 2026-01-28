package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class SettlementPage {

    private Page page;

    private Locator raiseSettlementBtn;
    private Locator apacInput;
    private Locator okButton;
    private Locator settlementHeader;

    public SettlementPage(Page page) {
        this.page = page;

        raiseSettlementBtn = page.locator("//span[text()='Raise Settlement Request']");
        apacInput = page.locator( "//input[contains(@class,'v-textfield') and contains(@class,'v-required')]");
        okButton = page.locator("//span[text()='Ok']");
        settlementHeader = page.locator("//span[text()='Raise Settlement Request']");
    }

    public void clickRaiseSettlement() {
        raiseSettlementBtn.waitFor();   // auto-wait
        raiseSettlementBtn.click();
    }

    public void enterApacAndSubmit(String apac) {
        apacInput.waitFor();
        apacInput.fill(apac);
        okButton.click();
    }

    public boolean isRaiseSettlementPageOpened() {
        settlementHeader.waitFor();
        return settlementHeader.isVisible();
    }
}
