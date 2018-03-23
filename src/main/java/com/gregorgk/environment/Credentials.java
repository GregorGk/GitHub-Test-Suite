package com.gregorgk.environment;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class Credentials {

  private String username;
  private String encryptedPassword;
  private JSONObject jsonObject = null;

  public Credentials() throws IOException {
    this.parseCredentials();
    this.username = (String) this.jsonObject.get("username");
    this.encryptedPassword = (String) this.jsonObject.get("encryptedPassword");
  }

  private void parseCredentials() throws IOException {
    File withCredentials = new File("src/main/resources/credentials.json");
    String contents = FileUtils.readFileToString(withCredentials, "UTF-8");
    this.jsonObject = new JSONObject(contents);
  }

  public String getUsername() {
    return username;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }
}
