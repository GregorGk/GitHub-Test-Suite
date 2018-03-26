package com.gregorgk;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;

import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.LoginPage;
import com.gregorgk.page.objects.RepositoryHandler;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class DeleteRepoTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();
  private static RepositoryHandler repositoryHandler;
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
    repositoryHandler.createRepository(TestConfig.getDriver());
  }

  @Before
  public void beforeTest() {
    TestConfig.getDriver().close();
    assertThat(repositoryHandler.repositorySet(),
        hasItem(repositoryHandler.getRepositoryName()));
    this.responseCode = repositoryHandler.deleteRepository();;
  }

  @Test
  public void deleteRepository() {
    assertEquals("Reponse code for repository deletion incorrect."
        + " Check or regenerate the access token.", 204, this.responseCode);
    Set<String> repositories = repositoryHandler.repositorySet();
    assertThat(repositories,
        not(hasItem(repositoryHandler.getRepositoryName())));
  }
}
