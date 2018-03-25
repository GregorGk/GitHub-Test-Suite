package com.gregorgk.environment;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class Credentials {

  private String username;
  private String encryptedPassword;
  private String token;
  private JSONObject jsonObject = null;

  /**
   * Constructs an object for credential handling.
   */
  public Credentials()  {
    this.parseCredentials();
    this.username = (String) this.jsonObject.get("username");
    this.encryptedPassword = (String) this.jsonObject.get("encryptedPassword");
    this.token = (String) this.jsonObject.get("token");
  }

  private void parseCredentials()  {
    File withCredentials = new File("src/main/resources/credentials.json");
    String contents = null;
    try {
      contents = FileUtils.readFileToString(withCredentials, "UTF-8");
    } catch (IOException e) {
      TestConfig.getLogger().log(Level.SEVERE, e, () -> e.toString());
    }
    this.jsonObject = new JSONObject(contents);
  }

  public String getUsername() {
    return username;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public String getToken() {
    return token;
  }
}
