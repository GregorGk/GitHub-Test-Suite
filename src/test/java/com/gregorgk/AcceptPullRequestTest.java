package com.gregorgk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.HeaderNavigation;
import com.gregorgk.page.objects.LoginPage;
import com.gregorgk.page.objects.PullRequestHandler;
import com.gregorgk.page.objects.RepositoryHandler;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class AcceptPullRequestTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();
  private static RepositoryHandler repositoryHandler;
  private static PullRequestHandler pullRequestHandler;
  private volatile int responseCode;

  /**
   * Prepares test environment and opens the right page.
   */
  @BeforeClass
  public static void setUp() {
    TestConfig.setUp();
    LoginPage loginPage = new LoginPage();
    loginPage.signIn(TestConfig.getDriver());
    repositoryHandler = new RepositoryHandler(TestConfig.getUsername());
    pullRequestHandler = new PullRequestHandler();
    repositoryHandler.createRepository(TestConfig.getDriver());
    repositoryHandler.importFromRepository(TestConfig.getDriver(),
        "https://github.com/GregorGk/Basic-Repository");
    HeaderNavigation headerNavigation = new HeaderNavigation();
    headerNavigation.singOut(TestConfig.getDriver());
    loginPage = new LoginPage();
    loginPage.signIn(TestConfig.getDriver());
    assertThat(repositoryHandler.repositorySet(),
        hasItem(repositoryHandler.getRepositoryName()));
    pullRequestHandler.openPullRequest(
        TestConfig.getDriver(),
        TestConfig.getUsername(),
        repositoryHandler.getRepositoryName(),
        "master",
        "develop");
    List<String> openedPullRequests =
        pullRequestHandler
            .listStatesOfPullRequests(repositoryHandler.getRepositoryName(), TestConfig.getUsername());
    assertNotNull("List of states of opened pull requests is null.",
        openedPullRequests);
    assertEquals("There is not exactly one pull request for the new repository.",
        1, openedPullRequests.size());
    assertEquals("There is not exactly one open pull request for the new repository.",
        "open", openedPullRequests.get(0));
    pullRequestHandler.mergePullRequest(TestConfig.getDriver(), TestConfig.getUsername(), repositoryHandler.getRepositoryName());
  }

  @Test
  public void assertThatTheOnlyOpenPullRequestHasBeenMerged() {
    List<String> openedPullRequests = new LinkedList<>();
    int tryCount = 0;
    do {
      openedPullRequests =
          pullRequestHandler
              .listStatesOfPullRequests(repositoryHandler.getRepositoryName(), TestConfig.getUsername());

      tryCount++;
    } while (openedPullRequests.size() != 0 && tryCount < 5);
    assertEquals(
        "There are no open pull request. The one open has already been merged.",
        0,
        openedPullRequests.size());
  }

  @After
  public void deleteRepository() {
    TestConfig.getDriver().quit();
    this.responseCode = repositoryHandler.deleteRepository();
    assertEquals("Response code for repository deletion incorrect."
        + " Check or regenerate the access token.", 204, this.responseCode);
  }
}
