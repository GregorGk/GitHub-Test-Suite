package com.gregorgk.suppliers;

import org.openqa.selenium.WebDriver;

public class WebDriverSupplierCache {

  private static volatile WebDriver chromeDriver;
  private static volatile WebDriver firefoxDriver;

  public static WebDriver getChromeDriver() {
    return chromeDriver;
  }

  public static synchronized void setChromeDriver(WebDriver chromeDriver) {
    WebDriverSupplierCache.chromeDriver = chromeDriver;
  }

  public static WebDriver getFirefoxDriver() {
    return firefoxDriver;
  }

  public static synchronized void setFirefoxDriver(WebDriver firefoxDriver) {
    WebDriverSupplierCache.firefoxDriver = firefoxDriver;
  }
}
