package com.gregorgk;

import com.google.common.base.Predicate;
import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.LoginPage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();

  @BeforeClass
  public static void setUp() throws IOException {
    TestConfig.setUp();
    LoginPage loginPage = new LoginPage();
    loginPage.signIn(TestConfig.getDriver());
  }

  @AfterClass
  public static void tearDown() {
    TestConfig.getDriver().close();
  }

  @Test
  public void assertSourceContainsSignedInMessage() {
    boolean thrown = false;
    try {
      new WebDriverWait(TestConfig.getDriver(), 10)
          .until((Predicate<WebDriver>) driver -> driver.getPageSource().contains("Signed in as"));
    } catch (Throwable e) {
      thrown = true;
      collector.addError(new Exception("Page source does not contain: Signed in as.", e));
    } finally {
      if (!thrown) {
        TestConfig.getLogger().info("Login successfull.");
      }
    }
  }

  @Test
  public void assertSourceContainsTheRightUsername() {
    boolean thrown = false;
    try {
      new WebDriverWait(TestConfig.getDriver(), 10)
          .until((Predicate<WebDriver>)
              driver -> driver.getPageSource().contains("HSBCapplicationTestUser"));
    } catch (Throwable e) {
      thrown = true;
      collector.addError(
          new Exception("Page source does not contain the username: HSBCapplicationTestUser.", e));
    } finally {
      if (!thrown) {
        TestConfig.getLogger().info("Logged in with the right username.");
      }
    }
  }
}
