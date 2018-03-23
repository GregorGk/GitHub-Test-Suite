package com.gregorgk.page.objects;

import com.gregorgk.environment.Credentials;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  private static String url = "https://github.com/login";

  @FindBy(id = "login_field")
  WebElement usernameField;

  @FindBy(id = "password")
  WebElement passwordField;

  @FindBy(name = "commit")
  WebElement loginButton;

  /**
   * Used to log in to GitHub.
   */
  public void signIn(WebDriver driver) throws IOException {
    PageFactory.initElements(driver, this);
    driver.get(LoginPage.getUrl());
    Credentials credentials = new Credentials();
    this.signIn(
        credentials.getUsername(),
        credentials.getEncryptedPassword());
  }

  private void signIn(
      String username,
      String password) {
    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    loginButton.click();
  }

  public static String getUrl() {
    return url;
  }
}
