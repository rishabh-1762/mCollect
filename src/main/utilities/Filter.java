package utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Map;

public class Filter {
    private Page page;
    private Locator addFilter;
    private Locator searchAutocompleteText;
    private Locator searchOption;
    private Locator selectBtn;
    private Locator filterInbox;
    private Locator refresh;
    private Locator body;


    public Filter(Page page){
        this.page=page;
        this.addFilter= page.locator("//span[text()=\"Add search condition\"]");
        this.searchAutocompleteText=page.getByPlaceholder("Search...");
        this.searchOption= page.locator("\"//span[text()=\"\"+Apac Card Number+\"\"]\"");
        this.selectBtn=page.locator("//span[text()=\"Select\"]");
        this.filterInbox=page.locator("//input[@class=\"v-textfield v-widget v-has-width\"]");
        this.refresh=page.locator("//span[text()=\"Refresh\"]");
        this.body=page.locator("//body");
    }
    public void refresh(){
        try {
            refresh.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
            refresh.click();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void selectColumnForFilter(Map<String,String> Entry){
        try {

            if (Entry != null && !Entry.isEmpty()) {
                for (Map.Entry<String, String> entry : Entry.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    addFilter.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
                    addFilter.click();
                    searchAutocompleteText.fill(key);
                    page.keyboard().press("Enter");
                    //body.click();
                    String DynamicXpath = String.format("xpath=//span[normalize-space(text())='%s']", key);
                    Locator locatorOptions = page.locator(DynamicXpath);
                    locatorOptions.waitFor();
                    locatorOptions.click();
                    selectBtn.click();
                    filterInbox.waitFor();
                    filterInbox.fill(value);
                    body.click();
                    page.waitForTimeout(30000);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
