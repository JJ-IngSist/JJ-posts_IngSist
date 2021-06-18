package com.edu.austral.ingsis.utils;

import com.edu.austral.ingsis.dtos.post.PostDTO;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ConnectMicroservices {

  @Value("${microservice.user.base-url}")
  private static String userUrl;

  private final static RestTemplate restTemplate = new RestTemplate();

  public static HttpEntity<Object> getRequestEntity(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    if (!token.isEmpty())
      headers.set("Authorization", token);
    return new HttpEntity<>(headers);
  }

  public static String getFromJson(String json, String property) {
    try {
      JSONObject fieldsJson = new JSONObject(json);
      return fieldsJson.getString(property);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String connectToUserMicroservice(String url, HttpMethod method, String token) {

    System.out.println(userUrl);
    final ResponseEntity<String> responseEntity = restTemplate.exchange("https://jibberjabber.hopto.org/api/user" + url,
            method,
            getRequestEntity(token),
            String.class);
    return responseEntity.getBody();
  }

  public static PostDTO setUserDetails(String json, PostDTO postDTO) {
    postDTO.setName(getFromJson(json, "name"));
    postDTO.setUsername(getFromJson(json, "username"));
    postDTO.setEmail(getFromJson(json, "email"));
    return postDTO;
  }
}
