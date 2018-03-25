package com.gregorgk;

import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.LoginPage;
import com.gregorgk.utils.Assertion;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class LogInTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();
  Assertion assertion = new Assertion();

  /**
   * Prepares test environment and opens the right page.
   */
  @BeforeClass
  public static void setUp() {
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
    assertion.assertSourceContains(
        "Signed in as",
        "Login successfull.",
        collector);
  }

  @Test
  public void assertSourceContainsTheRightUsername() {
    assertion.assertSourceContains(
        TestConfig.getUsername(),
        "Logged in with the right username.",
        collector);
  }
}
