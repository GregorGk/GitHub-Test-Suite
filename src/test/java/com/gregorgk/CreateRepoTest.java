package com.gregorgk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

import com.gregorgk.environment.TestConfig;
import com.gregorgk.page.objects.LoginPage;
import com.gregorgk.page.objects.RepositoryHandler;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class CreateRepoTest {


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
  }

  @Test
  public void createRepository() {
    repositoryHandler.createRepository(TestConfig.getDriver());
    Set<String> repositories = repositoryHandler.repositorySet();
    assertThat(repositories, hasItem(repositoryHandler.getRepositoryName()));
  }

  @AfterClass
  public static void tearDown() {
    repositoryHandler.deleteRepository();
    TestConfig.getDriver().close();
  }
}
