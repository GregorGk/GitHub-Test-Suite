package com.gregorgk;

import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.HeaderNavigation;
import com.gregorgk.page.objects.LoginPage;
import com.gregorgk.utils.Assertion;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class SignOutTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();
  Assertion assertion = new Assertion();

  /**
   * Prepares test environment and opens the right page.
   */
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
  public void assertThatSourceDoesNotContainSignedInMessage() {
    assertion.assertSourceDoesNotContain(
        "Signed in as",
        "Signout successfull.",
        collector);
  }

  @Test
  public void assertThatSourceDoesNotContainTheUsername() {
    assertion.assertSourceDoesNotContain(
        TestConfig.getUsername(),
        "No username after found log out.",
        collector);
  }

}
