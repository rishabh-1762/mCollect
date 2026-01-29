package pages;

import com.microsoft.playwright.Page;
import pages.settlement.RaiseSettlement;

public class SettlementPage {
    private Page page;
    private RaiseSettlement raiseSettlement;

    public SettlementPage(Page page) {
        this.page = page;
        this.raiseSettlement = new RaiseSettlement(page);
    }

    public void clickRaiseSettlement() {
        raiseSettlement.clickRaiseSettlementButton();
    }

    public void enterApacAndSubmit(String APAC) {
        raiseSettlement.enterAPACforSettlementRequest(APAC);
        raiseSettlement.clickOkButton();
    }

    public boolean isRaiseSettlementPageOpened() {
        return raiseSettlement.isRaiseSettlementPageOpened();
    }
}