package com.edu.austral.ingsis.utils;

import com.edu.austral.ingsis.dtos.post.PostDTO;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ConnectMicroservices {

  private final static RestTemplate restTemplate = new RestTemplate();

  public static HttpEntity<Object> getRequestEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
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

  public static String connectToUserMicroservice(String url, HttpMethod method) {
    final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080" + url,
            method,
            getRequestEntity(),
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
