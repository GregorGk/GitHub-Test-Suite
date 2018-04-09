package com.gregorgk.page.objects;

import com.gregorgk.utils.HttpHandler;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PullRequestHandler {

  @FindBy(xpath = "//button[contains(.,'Create pull request')]")
  WebElement createPulLRequestButton;

  @FindBy(css = "#new_pull_request > div.discussion-timeline > div > div > div.form-actions > button")
  WebElement pullRequestCreationConfirmationButton;

  @FindBy(xpath = "//button[contains(.,'Merge pull request')]")
  WebElement mergePullRequestButton;

  @FindBy(css = "#partial-pull-merging > div.merge-pr.js-merge-pr.js-details-container.Details.is-merging.js-transitionable.open.Details--on > form > div > div.commit-form-actions > div > div.BtnGroup.btn-group-merge > button")
  WebElement mergeConfirmationButton;

  public void openPullRequest(
      WebDriver driver,
      String username,
      String repositoryName,
      String targetBranch,
      String sourceBranch) {
    do {
      driver.get("https://github.com/"
          + username + "/"
          + repositoryName + "/"
          + "compare/"
          + targetBranch
          + "..."
          + sourceBranch);
    } while (!driver.getTitle().contains("Comparing " + targetBranch + "..." + sourceBranch));
    PageFactory.initElements(driver, this);
    createPulLRequestButton.click();
    ((JavascriptExecutor) driver)
        .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    PageFactory.initElements(driver, this);
    pullRequestCreationConfirmationButton.click();
  }

  public void mergePullRequest(
      WebDriver driver,
      String username,
      String repositoryName) {
    driver.get("https://github.com/"
        + username + "/"
        + repositoryName + "/pull/1");
    PageFactory.initElements(driver, this);
    try {
      mergePullRequestButton.click();
    } catch (StaleElementReferenceException e) {
      this.mergePullRequest(driver, username, repositoryName);
    }
    PageFactory.initElements(driver, this);
    mergeConfirmationButton.click();
    System.out.println();
  }

  public synchronized List<String> listStatesOfPullRequests(
      String repoName,
      String username) {
    List<String> pullRequests = new LinkedList<>();
    String response = HttpHandler.makeGetRequest(
        "repos/" + username + "/" + repoName + "/" + "pulls");
    JSONArray array = new JSONArray(response);
    for (int i = 0; i < array.length(); ++i) {
      pullRequests.add(array.getJSONObject(i).getString("state"));
    }
    return pullRequests;
  }
}
