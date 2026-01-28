package Settlement;

import baseTest.BaseTestClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.Filter;
import utilities.Main_menu;
import utilities.TestDataProvider;
public class navigateToRaiseSettlement extends BaseTestClass{


    private LoginPage loginPage;
    private Filter filter;

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class)
    public void testSettlementNavigation(String userName, String password, String menu, String subMenu) {
        // 1. Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(userName, password);

        // 2. Navigate to Settlement
        Main_menu mainMenu = new Main_menu(page);
        mainMenu.selectMainmenu(menu, subMenu);




    }
}
