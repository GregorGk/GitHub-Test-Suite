package com.gregorgk.environment;

import com.gregorgk.suppliers.WebDriverSupplier;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;

public class TestConfig {

  private static final Logger LOGGER
      = Logger.getLogger(TestConfig.class.getName());
  private static WebDriver driver;
  private static Credentials credentials;

  public static Logger getLogger() {
    return LOGGER;
  }

  /**
   * Used to set up logger and browser.
   */
  public static synchronized void setUp() {
    driver = new WebDriverSupplier().get();
    credentials = new Credentials();
  }

  /**
   * Used to destroy browser session.
   */
  public static synchronized void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  public static WebDriver getDriver() {
    return driver;
  }

  public static String getUsername() {
    return credentials.getUsername();
  }

  public static String getToken() {
    return credentials.getToken();
  }
}
