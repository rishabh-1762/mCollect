package pages;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPage {  // Class name capitalized (Java convention)
    private Page page;

    // ✅ 1. DECLARE page FIRST
    private Locator username;
    private Locator password;
    private Locator loginBtn;
    private Locator concurrentLogin;

    // ✅ 2. INITIALIZE in constructor
    public LoginPage(Page page) {
        this.page = page;
        // ✅ 3. Initialize locators AFTER page assignment
        this.username = page.getByPlaceholder("User name");
        this.password = page.getByPlaceholder("Password");
        this.loginBtn = page.locator("(//div[@role=\"button\"])[4]");// Fixed: was "getByPlaceholder"
        this.concurrentLogin=page.locator("(//div//div[@role=\"button\"]//span[@class=\"v-button-caption\"])[5]");
    }

    // ✅ Usage methods
    public void login(String user, String pass) {

        page.keyboard().press("Control+r");
        page.waitForLoadState(LoadState.LOAD);
        username.waitFor();
        username.fill(user);
        password.waitFor();
        password.fill(pass);
        loginBtn.click();

        try {
            concurrentLogin.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
            if (concurrentLogin.isVisible()) {
                concurrentLogin.click();
            }
        } catch (TimeoutError e) {
            System.out.println("Concurrent login popup not appeared, continuing...");
            // Test continues normally
        }
    }

}

