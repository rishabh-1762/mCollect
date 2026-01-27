package Settlement;

import baseTest.BaseTestClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.Filter;
import utilities.Main_menu;

public class APACselectionTest extends BaseTestClass {

    private LoginPage loginPage;
    private Filter filter;

    @Test(dataProvider = "settlementLoginCredentials")
    public void fetchApac(String menu, String subMenu, String vertical) {

        loginPage=new LoginPage(page);
        Main_menu mainMenu = new Main_menu(page);
        filter=new Filter(page);
        loginPage.login(userName,password);
        mainMenu.selectMainmenu(menu, subMenu);

        Filter.apply("Vertical", "contains", vertical);

        String apac = new CaseMasterPage(page).getFirstApacNumber();
        Assert.assertNotNull(apac);
    }



}
}
