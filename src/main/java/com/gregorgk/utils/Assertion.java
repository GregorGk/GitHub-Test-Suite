package com.gregorgk.utils;

import com.gregorgk.environment.TestConfig;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Assertion {

  public void assertSourceContains(
      String searchedText,
      String successMessage,
      ErrorCollector collector) {
    assertSource(
        searchedText, successMessage, true, collector);
  }

  public void assertSourceDoesNotContain(
      String searchedText,
      String successMessage,
      ErrorCollector collector) {
    assertSource(
        searchedText, successMessage, false, collector);
  }

  private void assertSource(
      String searchedText,
      String successMessage,
      boolean positive,
      ErrorCollector collector) {
    boolean thrown = false;
    try {
      new WebDriverWait(TestConfig.getDriver(), 10)
          .until(driver ->
              positive
                  ?
                  driver.getPageSource().contains(searchedText)
                  : !driver.getPageSource().contains(searchedText));
    } catch (Throwable e) {
      thrown = true;
      collector.addError(
          new Exception(
              String.format("Page source %s: %s.",
                  positive ? "does not contain" : "contains", searchedText), e));
    } finally {
      if (!thrown) {
        TestConfig.getLogger().info(successMessage);
      }
    }
  }
}
