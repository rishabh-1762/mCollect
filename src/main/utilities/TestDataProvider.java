package utilities;

import org.testng.annotations.DataProvider;

import java.util.Arrays;


public class TestDataProvider {

    // For login - return only first 2 columns
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelReader.getDataWithColumns("settlementLoginCredentials", 2);  // Only username & password
    }

}