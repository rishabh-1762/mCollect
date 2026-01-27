package Settlement;

import baseTest.BaseTestClass;
import org.testng.annotations.Test;
import pages.Allocation;
import pages.LoginPage;
import utilities.Filter;
import utilities.Main_menu;

import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class APACselectionTest extends BaseTestClass {

    private LoginPage loginPage;
    private Allocation allocation;
    private Filter filter;

    @Test(dataProvider = "loginData",
            dataProviderClass = utilities.TestDataProvider.class)
    public void selectVerticalAndPendingWith(String screenName
            ,String verticleName,String apacNumber,String colHeader){
        Map<String,String> filterCol=new HashMap<>();
        filterCol.put(colHeader, apacNumber);
        loginPage=new LoginPage(page);
        allocation=new Allocation(page);
        Main_menu menu=new Main_menu(page);
        filter=new Filter(page);
        loginPage.login(userName,password);
        menu.selectMainmenu(module,screenName);
        allocation.selectVertAndPendingwith(verticleName,pendingWith);
        filter.refresh();
        filter.selectColumnForFilter(filterCol);
        Map<String, String> resultMap = allocation.allocation(allocatedTo,apacNumber);
        assertTrue(resultMap.containsKey(apacNumber));


    }
}
