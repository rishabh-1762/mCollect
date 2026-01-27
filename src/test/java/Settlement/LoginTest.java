package Settlement;

import baseTest.BaseTestClass;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import org.testng.annotations.Test;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTestClass {
    private LoginPage loginPage;


    @Test(dataProvider = "loginData",
            dataProviderClass = utilities.TestDataProvider.class)
    public void validLoginTest(String username, String password) {
        loginPage = new LoginPage(page);
        loginPage.login(username, password);


        // Verify login successful - Check dashboard is visible
        Locator dashboard = page.locator("//img[@class='jmix-image v-widget v-has-width object-fit-fill']");
        assertThat(dashboard).isVisible(new LocatorAssertions.IsVisibleOptions()
                .setTimeout(3000));
    }
}