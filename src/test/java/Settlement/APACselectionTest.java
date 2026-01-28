package Settlement;
import com.microsoft.playwright.*;
import baseTest.BaseTestClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CaseMaster;
import pages.LoginPage;
import utilities.Filter;
import utilities.Main_menu;
import utilities.TestDataProvider;

public class APACselectionTest extends BaseTestClass {


    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class)
    public void testCasesMasterNavigation(String userName, String password, String menu, String subMenu, String vertical) {
        // 1. Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(userName, password);

        // 2. Navigate to Cases Master
        Main_menu mainMenu = new Main_menu(page);
        mainMenu.selectMainmenu(menu, subMenu);

        // 3. Apply Filter -
        Filter filter = new Filter(page);
        filter.apply("Vertical", "contains", vertical);

        // 4. Get APAC number and assert
        String apac = new CaseMaster(page).getFirstApacNumber();
        Assert.assertNotNull(apac, "APAC number should not be null");
    }

}

