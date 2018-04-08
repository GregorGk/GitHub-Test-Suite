package com.gregorgk.environment;

import com.google.common.base.Strings;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class Credentials {

  private String username;
  private String password;
  private String token;
  private JSONObject jsonObject = null;

  /**
   * Constructs an object for credential handling.
   */
  public Credentials()  {
    String[] propertiesNames = new String[] {"username", "password", "token"};
    if (areCredentialsAvailableAsSystemProperties(propertiesNames)) {
      this.setFromSystemProperties(propertiesNames);
    } else {
      this.setFromJson();
    }
  }

  private void setFromJson() {
    this.parseCredentials();
    this.username = (String) this.jsonObject.get("username");
    this.password = new String(Base64.decodeBase64(
        (String) this.jsonObject.get("encodedPassword")));
    this.token = new String(Base64.decodeBase64(
        (String) this.jsonObject.get("encodedToken")));
  }

  private boolean areCredentialsAvailableAsSystemProperties(String[] propertiesNames) {
    return
        propertiesNames != null
        && propertiesNames.length == 3
        && !Strings.isNullOrEmpty(System.getProperty(propertiesNames[0]))
        && !Strings.isNullOrEmpty(System.getProperty(propertiesNames[1]))
        && !Strings.isNullOrEmpty(System.getProperty(propertiesNames[2]));
  }

  private void setFromSystemProperties(String[] propertiesNames) {
    this.username = System.getProperty(propertiesNames[0]);
    this.password = System.getProperty(propertiesNames[1]);
    this.token = System.getProperty(propertiesNames[2]);
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

  public String getPassword() {
    return password;
  }

  public String getToken() {
    return token;
  }
}
