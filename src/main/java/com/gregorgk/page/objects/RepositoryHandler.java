package com.gregorgk.page.objects;

import com.gregorgk.utils.HttpHandler;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RepositoryHandler {

  private static String url = "https://github.com/";

  @FindBy(partialLinkText = "Start a project")
  WebElement startProjectButton;

  @FindBy(id = "repository_name")
  WebElement nameField;

  @FindBy(xpath = "//*[@id=\"new_repository\"]/div[3]/button")
  WebElement submitButton;

  @FindBy(xpath = "//*[@id=\"js-repo-pjax-container\"]/div[1]/nav/a[4]")
  WebElement settings;

  @FindBy(xpath = "(//*[text()[contains(., 'Delete this repository')]])[1]")
  WebElement deleteButton;

  @FindBy(xpath = "//input[@aria-label="
      + "\"Type in the name of the repository to confirm that you want to delete this repository.\"]")
  WebElement inputField;

  @FindBy(xpath = "//*[text() = 'foobar']")
  WebElement submitRemoval;

  private String repositoryName;
  private Set<String> repositorySet;
  private String username;

  public RepositoryHandler(String username) {
    this.username = username;
    this.repositorySet = this.repositorySet();
    this.repositoryName = this.pickRepositoryName();
  }

  /**
   * Creates a repository.
   * @return name of the created repository.
   */
  public synchronized String createRepository(WebDriver driver) {
    driver.get(RepositoryHandler.url);
    PageFactory.initElements(driver, this);
    startProjectButton.click();
    PageFactory.initElements(driver, this);
    nameField.sendKeys(this.repositoryName);
    PageFactory.initElements(driver, this);
    submitButton.submit();
    return this.getRepositoryName();
  }

  /**
   * Deletes this repository.
   */
  public synchronized int deleteRepository() {
    return HttpHandler.makeDeleteRequestWithToken(this.repositoryName);
  }

  /**
   * Uses recursion to pick a correct repository name.
   */
  private String pickRepositoryName() {
    String nameCandidate = RandomStringUtils.randomAlphabetic(5, 10);
    return this.repositorySet.contains(nameCandidate)
        ?
        this.pickRepositoryName() : nameCandidate;
  }

  public synchronized Set<String> repositorySet() {
    Set<String> repositories = new HashSet<>();
    String response = HttpHandler.makeGetRequest(this.username + "/repos");
    JSONArray array = new JSONArray(response);
    for (int i = 0; i < array.length(); ++i) {
      repositories.add(array.getJSONObject(i).getString("name"));
    }
    return repositories;
  }

  public String getRepositoryName() {
    return repositoryName;
  }
}
