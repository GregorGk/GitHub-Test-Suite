package com.gregorgk;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.LoginPage;
import com.gregorgk.page.objects.RepositoryHandler;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class DeleteRepoTest {

  @Rule
  public ErrorCollector collector = new ErrorCollector();
  private static RepositoryHandler repositoryHandler;

  /**
   * Prepares test environment and opens the right page.
   */
  @BeforeClass
  public static void setUp() {
    TestConfig.setUp();
    repositoryHandler = new RepositoryHandler(TestConfig.getUsername());
    LoginPage loginPage = new LoginPage();
    loginPage.signIn(TestConfig.getDriver());
    DeleteRepoTest.createRepository();
  }

  public static void createRepository() {
    repositoryHandler.createRepository(TestConfig.getDriver());
  }

  @Test
  public void deleteRepository() {
    repositoryHandler.deleteRepository();
    Set<String> repositories = repositoryHandler.repositorySet(TestConfig.getUsername());
    TestConfig.getDriver().close();
    assertThat(repositories, not(hasItem(repositoryHandler.getRepositoryName())));
  }
}
