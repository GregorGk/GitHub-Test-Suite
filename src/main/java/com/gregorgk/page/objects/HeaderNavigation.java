package com.gregorgk.page.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderNavigation {

  private static String url = "https://github.com/";

  @FindBy(xpath = "//*[@id=\"user-links\"]/li[3]/details/summary/img")
  WebElement headerNavigationLink;

  @FindBy(xpath = "//*[@id=\"user-links\"]/li[3]/details/ul/li[9]/form/button")
  WebElement signOutButton;

  /**
   * Signs out using the header navigation.
   */
  public void singOut(WebDriver driver) {
    PageFactory.initElements(driver, this);
    if (!driver.getCurrentUrl().equals(HeaderNavigation.url)) {
      driver.get(LoginPage.getUrl());
    }
    headerNavigationLink.click();
    signOutButton.click();
  }
}
