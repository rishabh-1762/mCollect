package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Allocation {

    private Page page;
    private Locator verticalTextBox;
    private Locator verticalOpsPopUp;
    private Locator pendingWithText;
    private Locator searchIcon;
    private Locator allocateToText;
    private Locator allocateBtn;
    private Locator ApacNumber;
    private Locator toaster;
    private Locator allocateTextBox;


    public Allocation(Page page){
        this.page = page;
        this.verticalTextBox=page.locator("(//div[@class=\"v-filterselect-button\"])[1]");
        this.pendingWithText=page.locator("(//div[@class=\"v-filterselect-button\"])[2]");
        this.allocateTextBox=page.locator("(//div[@class=\"v-filterselect-button\"])[3]");
        this.searchIcon=page.locator("//div[@class=\"v-slot v-slot-jmix-primary-action v-align-right v-align-bottom\"]");
        this.allocateToText=page.locator("(//input[@class=\"v-filterselect-input\"])[3]");
        this.allocateBtn=page.locator("//span[text()=\"Allocate\"]");
        this.ApacNumber=page.locator("//tr[@class='v-table-row']/td[24]");
        this.toaster=page.locator("//div[@role=\"alert\"]");
        this.verticalOpsPopUp=page.locator("//div[@class=\"v-filterselect-suggestmenu\"]//td//span");
        //this.allocateTextBox=page.locator("");
    }

    public void selectVertAndPendingwith(String vertical,String pendingWith){
        try {
            verticalTextBox.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
            verticalTextBox.click();
            page.waitForTimeout(300);
            List<Locator> rowList = verticalOpsPopUp.all();
            for(Locator verticle:rowList){
                String verticleName=verticle.textContent().trim();
                if(vertical.equalsIgnoreCase(verticleName)){
                    verticle.click();
                    break;
                }
            }
            pendingWithText.click();
            page.waitForTimeout(300);
            List<Locator> userList = verticalOpsPopUp.all();
            for(Locator userslist:userList){
                String userName=userslist.textContent().trim();
                if(pendingWith.equalsIgnoreCase(userName)){
                    userslist.click();
                    break;
                }
            }
            searchIcon.click();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Map<String,String> allocation(String allocateToUser,String uniqValue){
        String uniqId=null;
        String message=null;
        try{

            //Locator twentyThirdCells = page.locator("c");
            //ApacNumber.waitFor();
            List<Locator> all23rdCells = ApacNumber.all();
            for(Locator apacNum:all23rdCells) {
                uniqId = apacNum.textContent().trim();
                if (uniqId.equalsIgnoreCase(uniqValue)) {
                    apacNum.click();
                    break;
                }
            }
            allocateTextBox.waitFor();
            allocateTextBox.click();
            page.waitForTimeout(300);
            List<Locator> userList = verticalOpsPopUp.all();
            for(Locator allocateUserlist:userList){
                String userName=allocateUserlist.textContent().trim();
                if(allocateToUser.equalsIgnoreCase(userName)){
                    allocateUserlist.click();
                    break;
                }
            }
                allocateBtn.click();
                toaster.waitFor();
                 message=toaster.textContent().trim();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new HashMap<>(Map.of(
                uniqId,message
        ));
    }


}
