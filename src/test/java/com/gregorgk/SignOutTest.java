package com.gregorgk;

import com.google.common.base.Predicate;
import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.HeaderNavigation;
import com.gregorgk.page.objects.LoginPage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignOutTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();

  @BeforeClass
  public static void setUp() throws IOException {
    TestConfig.setUp();
    LoginPage loginPage = new LoginPage();
    loginPage.signIn(TestConfig.getDriver());
    HeaderNavigation headerNavigation = new HeaderNavigation();
    headerNavigation.singOut(TestConfig.getDriver());
  }

  @AfterClass
  public static void tearDown() {
    TestConfig.getDriver().close();
  }


  @Test
  public void assertThatSourceDoesNotContainTheUsername() {
    boolean thrown = false;
    try {
      new WebDriverWait(TestConfig.getDriver(), 10)
          .until((Predicate<WebDriver>) driver -> !driver.getPageSource().contains("HSBCapplicationTestUser"));
    } catch (Throwable e) {
      thrown = true;
      collector.addError(new Exception("Page source contains the username: HSBCapplicationTestUser.", e));    } finally {
      if (!thrown) {
        TestConfig.getLogger().info( "No username after found log out.");
      }
    }
  }

  @Test
  public void assertThatSourceDoesNotContainSignedInMessage() {
    boolean thrown = false;
    try {
      new WebDriverWait(TestConfig.getDriver(), 10)
          .until((Predicate<WebDriver>) driver -> !driver.getPageSource().contains("Sigccned in as"));
    } catch (Throwable e) {
      thrown = true;
      collector.addError(new Exception("Page source contains: Signed in as.", e));
    } finally {
      if (!thrown) {
        TestConfig.getLogger().info("Signed out successfully.");
      }
    }
  }
}
