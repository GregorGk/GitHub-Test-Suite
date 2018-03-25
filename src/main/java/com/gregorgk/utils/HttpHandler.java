package com.gregorgk.utils;

import com.gregorgk.environment.TestConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class HttpHandler {

  public static synchronized String makeGetRequest(String urn) {
    return HttpHandler.makeRequest("https://api.github.com/users/" + urn,
          "GET");
  }

  public static synchronized int makeDeleteRequestWithToken(String repoName) {
    int responseCode = 0;
    try {
      URL url = new URL("https://api.github.com/repos/"
          + TestConfig.getUsername() + "/" + repoName
          + "?access_token=" + TestConfig.getToken());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("DELETE");
      responseCode = connection.getResponseCode();
    } catch (Exception e) {
      TestConfig.getLogger().log(Level.SEVERE, e, () -> e.toString());
    }
    return responseCode;
  }

  private static synchronized String makeRequest(String urlText, String method) {
    StringBuilder result;
    URL url = null;
    try {
      url = new URL(urlText);
    } catch (MalformedURLException e) {
      String malformedUrl = url.toString();
      TestConfig.getLogger().log(Level.SEVERE, e,
          () -> String.format("Error with the URL candidate: %s.", malformedUrl));
    }
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod(method);
    } catch (IOException e) {
      TestConfig.getLogger().log(Level.SEVERE, e, () -> e.toString());
    }
    result = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line);
      }
    } catch (IOException e) {
      TestConfig.getLogger().log(Level.SEVERE, e, () -> "Error with Github HTTP request.");
    }
    return result.toString();
  }
}
