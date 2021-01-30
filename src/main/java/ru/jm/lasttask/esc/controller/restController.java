package ru.jm.lasttask.esc.controller;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.jm.lasttask.esc.entity.User;

public class restController {

    static final String URL = "http://91.241.64.178:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();
    static String resultHeader= "";

    public static void main(String[] args) {
        String cookie = getUsers();
        resultHeader += createUser(cookie);
        System.out.println(resultHeader + " - " + "createUser");
        resultHeader += updateUser(cookie);
        System.out.println(resultHeader + " - " + "updateUser");
        resultHeader += deleteUser(cookie);
        System.out.println(resultHeader + " - " + "deleteUser");
        System.out.println(resultHeader + " - " + "result");
    }

    private static String getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL,
                HttpMethod.GET, entity, String.class);
        String cookie = response.getHeaders().getFirst("Set-Cookie");
        return cookie;
    }

    private static String createUser(String cookie) {
        User user = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL,
                HttpMethod.POST, request, String.class);
        return response.getBody();
    }

    private static String updateUser(String cookie) {
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 31);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<User> request = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL,
                HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    private static String deleteUser(String cookie) {
        String url = URL + "/3";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }

}
