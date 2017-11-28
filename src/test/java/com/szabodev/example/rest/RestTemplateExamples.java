package com.szabodev.example.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {

    private static final String API_URL = "https://api.predic8.de:443/shop";

    @Test
    public void getCategories() throws Exception {
        String apiUrl = API_URL + "/categories/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void getCustomers() throws Exception {
        String apiUrl = API_URL + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void createCustomer() throws Exception {
        String apiUrl = API_URL + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Joe");
        postMap.put("lastname", "Buck");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);
    }

    @Test
    public void updateCustomer() throws Exception {
        String apiUrl = API_URL + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        // Create user
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Micheal");
        postMap.put("lastname", "Weston");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        // Update user
        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);
        postMap.put("firstname", "Micheal 2");
        postMap.put("lastname", "Weston 2");
        restTemplate.put(apiUrl + id, postMap);
        JsonNode updatedNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        System.out.println("Response after update: " + updatedNode);
    }

    @Test(expected = ResourceAccessException.class)
    public void updateCustomerUsingPatchSunHttp() throws Exception {
        String apiUrl = API_URL + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        // Create user
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        // Update
        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);
        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);
        //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);
        System.out.println("Response after update: " + updatedNode);
    }

    @Test
    public void updateCustomerUsingPatch() throws Exception {
        String apiUrl = API_URL + "/customers/";
        // Use Apache HTTP client factory
        // see: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // Create user
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        // Update user
        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);
        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);
        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);
        System.out.println("Response after update: " + updatedNode);
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() throws Exception {
        String apiUrl = API_URL + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        // Create user
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Les");
        postMap.put("lastname", "Claypool");
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response: " + jsonNode);

        // Delete user
        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("Created customer id: " + id);
        restTemplate.delete(apiUrl + id); //expects 200 status
        System.out.println("Customer deleted");

        restTemplate.getForObject(apiUrl + id, JsonNode.class);
    }

}
