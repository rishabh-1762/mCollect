package tests;

import baseTest.BaseTestClass;


import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.Main_menu;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTestClass {

        private LoginPage loginPage;

        @Test
        public void validLoginTest() {
            loginPage = new LoginPage(page);
            loginPage.login("vasu_admin", "Admin@123");
            Main_menu mn=new Main_menu(page);
            mn.selectMainmenu("Allocation","Manual allocation");
            //mn.selectSubmenu("Manual allocation");
            assertTrue(page.isVisible("//div[@class=\"v-label v-widget jmix-breadcrumbs-win-caption v-label-jmix-breadcrumbs-win-caption v-label-undef-w\"]"));
            //assertTrue(page.isVisible("//img[@object-fit-id='4']"));
        }
    }


