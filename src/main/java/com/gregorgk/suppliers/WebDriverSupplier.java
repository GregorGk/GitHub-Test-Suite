package com.gregorgk.suppliers;

import com.google.common.base.Supplier;
import com.gregorgk.utils.BrowserEnum;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverSupplier implements Supplier<WebDriver> {

  private BrowserEnum browser;

  /**
   * Used to parse the optional {@code -Dbrowser} argument from Maven console options.
   */
  public WebDriverSupplier() {
    try {
      this.browser = BrowserEnum.valueOf(System.getProperty("browser").toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      this.browser = BrowserEnum.CHROME;
    }
  }

  /**
   * Used to get the {@code WebDriver}.
   *
   * @return implementation of {@code WebDriver} interface according to the browser type given in
   *         constructor.
   */
  public WebDriver get() {
    WebDriver toReturn = null;
    if (browser.equals(BrowserEnum.CHROME)) {
      if (WebDriverSupplierCache.getChromeDriver() == null) {
        WebDriverManager.chromedriver().setup();
        toReturn = new ChromeDriver();
      } else {
        toReturn = WebDriverSupplierCache.getChromeDriver();
      }
    } else if (browser.equals(BrowserEnum.FIREFOX)) {
      if (WebDriverSupplierCache.getFirefoxDriver() == null) {
        WebDriverManager.firefoxdriver().setup();
        toReturn = new FirefoxDriver();
      } else {
        toReturn = WebDriverSupplierCache.getFirefoxDriver();
      }
    }
    return toReturn;
  }
}
