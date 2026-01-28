package utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Main_menu {
    private Page page;
    private Locator centerLogo;

    public Main_menu(Page page) {
        this.page = page;
        this.centerLogo = page.locator("(//div[@class=\"v-slot v-align-center v-align-middle\"]//img)[1]");
    }

    public void selectMainmenu(String menu, String sub) {
        try {
            // 1. Wait for page to be ready
            centerLogo.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));

            // 2. Click main menu (more flexible xpath)
            String mainMenuXpath = "//span[normalize-space(text())='" + menu + "']";
            Locator mainMenuLocator = page.locator(mainMenuXpath).first();

            System.out.println("Clicking main menu: " + menu);
            mainMenuLocator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            mainMenuLocator.click();

            // 3. Wait for submenu to expand (give animation time)
            page.waitForTimeout(500);  // Small wait for animation

            // 4. Click submenu (more flexible - doesn't require "open" class)
            String subMenuXpath = "//div[contains(@class,'jmix-sidemenu-submenu')]//span[normalize-space(text())='" + sub + "']";
            Locator subMenuLocator = page.locator(subMenuXpath).first();

            System.out.println("Clicking submenu: " + sub);
            subMenuLocator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            subMenuLocator.click();

            // 5. Wait for navigation to complete
            page.waitForLoadState();
            System.out.println("✅ Successfully navigated to: " + menu + " -> " + sub);

        } catch (Exception e) {
            System.err.println("Failed to navigate to: " + menu + " -> " + sub);
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("Navigation failed: " + menu + " -> " + sub, e);
        }
    }

    public void selectSubmenu(String sub) {
        try {
            // More flexible xpath - works whether menu is open or not
            String subMenuXpath = "//div[contains(@class,'jmix-sidemenu-submenu')]//span[normalize-space(text())='" + sub + "']";
            Locator subMenuLocator = page.locator(subMenuXpath).first();

            System.out.println("Clicking submenu: " + sub);
            subMenuLocator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            subMenuLocator.click();

            page.waitForLoadState();
            System.out.println("Successfully clicked submenu: " + sub);

        } catch (Exception e) {
            System.err.println("Failed to click submenu: " + sub);
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("Submenu click failed: " + sub, e);
        }
    }

    // 🆕 Helper method to check if menu is already expanded
    public boolean isMenuExpanded(String menu) {
        String xpath = "//span[text()='" + menu + "']/ancestor::div[contains(@class,'jmix-sidemenu-item-header')]" +
                "/following-sibling::div[contains(@class,'jmix-sidemenu-submenu-open')]";
        return page.locator(xpath).count() > 0;
    }
}