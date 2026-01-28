package utilities;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return ExcelReader.getDataWithColumns("settlementLoginCredentials", 5);  // Only username & password
    }

    @DataProvider(name = "SettlementData")
    public static Object[][] getSettlementData() {
        return ExcelReader.getDataWithColumns("settlementLoginCredentials", 5);  // All 5 columns: username, password, Menu, SubMenu, APAC
    }
}