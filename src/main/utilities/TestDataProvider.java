package utilities;

import org.testng.annotations.DataProvider;



public class TestDataProvider {


    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return ExcelReader.getDataWithColumns("settlementLoginCredentials",4);  // Only username & password
    }

}