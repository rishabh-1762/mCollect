package pages.settlement;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class RaiseSettlement {

    private Page page;
    private Locator raiseSettlementRequest;
    private Locator apacNumber;
    private Locator okButton;
    private Locator continueWithBothInParallel;


    //Constructor
    public RaiseSettlement(Page page){
        this.page = page;
        this.raiseSettlementRequest=page.locator("//div[@class='v-button v-widget btn-white v-button-btn-white icon']");
        this.apacNumber= page.locator("//div[@class='v-textfield v-textfield-error-error v-widget v-textfield-error v-required v-textfield-required v-has-width']");
        this.okButton = page.locator("//div[@class='v-button v-widget']");
        this.continueWithBothInParallel = page.getByLabel("Continue with both in parallel ");


    }
    public void enterAPACforSettlementRequest(String APACNumber){

    }

}
