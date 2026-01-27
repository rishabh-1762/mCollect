package utilities;

import org.testng.annotations.DataProvider;

import java.util.Arrays;


public class TestDataProvider {

    // For login - return only first 2 columns
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelReader.getDataWithColumns("settlementLoginCredentials", 2);  // Only username & password
    }
    // For settlement - return all 9 columns
    @DataProvider(name = "loginData")
    public Object[][] getAPACNumberData() {
        return ExcelReader.getData("selectAPACnumber");  // All 9 columns
    }
}