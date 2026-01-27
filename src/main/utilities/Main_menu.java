package utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

public class Main_menu {
    private Page page;

    // ✅ 1. DECLARE page FIRST
    private Locator mainMenu;
    private Locator subMenu;
    private Locator centerLogo;
    private Locator mainMenuXpath;
    private Locator subMenuXpath;


public Main_menu(Page page){
    this.page=page;

    this.mainMenu =page.locator("//div[@class=\"jmix-sidemenu-item jmix-sidemenu-item-header\"]");
    this.subMenu=page.locator("//div[@class=\"jmix-sidemenu-submenu jmix-sidemenu-submenu-open\"]//div");
    this.centerLogo=page.locator("(//div[@class=\"v-slot v-align-center v-align-middle\"]//img)[1]");

}
public void selectMainmenu(String menu,String sub) {
    centerLogo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
    String  mainMenuXpath="(//span[text()='"+menu+"'])[1]";
    String subMenuXpath = "(//div[@class=\"jmix-sidemenu-submenu jmix-sidemenu-submenu-open\"]//span//span[text()='"+sub+"'])[1]";
    Locator mainMenuXpat=page.locator(mainMenuXpath);
    mainMenuXpat.click();
    Locator subMenuXpathLocator = page.locator(subMenuXpath);
    subMenuXpathLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
    subMenuXpathLocator.click();

    }

    public void selectSubmenu(String sub){
        String subMenuXpath = "//div[@class=\"jmix-sidemenu-submenu jmix-sidemenu-submenu-open\"]//span//span[text()='"+sub+"']";
        Locator subMenuXpathLocator = page.locator(subMenuXpath);
        subMenuXpathLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
        subMenuXpathLocator.click();


    }
}
