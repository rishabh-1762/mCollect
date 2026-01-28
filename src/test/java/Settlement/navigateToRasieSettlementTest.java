package Settlement;

import baseTest.BaseTestClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;
import utilities.Main_menu;
import pages.SettlementPage;
import utilities.ExcelReader;

public class navigateToRasieSettlementTest extends BaseTestClass {

    @Test(dataProvider = "SettlementData", dataProviderClass = utilities.TestDataProvider.class)
    public void raiseSettlementUsingApac(String userName, String password, String menu, String subMenu, String APAC) {

        // 1. Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(userName, password);

        // 2. Navigate to Settlement
        Main_menu mainMenu = new Main_menu(page);
        mainMenu.selectMainmenu(menu, subMenu);

        // 3. Raise Settlement with APAC
        SettlementPage settlementPage = new SettlementPage(page);
        settlementPage.clickRaiseSettlement();
        settlementPage.enterApacAndSubmit(APAC);

        Assert.assertTrue(settlementPage.isRaiseSettlementPageOpened());
        }
}

