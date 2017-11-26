 package com.szabodev.example.rest.service;

import com.szabodev.example.rest.domain.User;
import com.szabodev.example.rest.domain.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final static String API_URL = "http://apifaketory.com/api/user";

    private final RestTemplate restTemplate;

    @Autowired
    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        UserData userData = restTemplate.getForObject(API_URL + "?limit=" + limit, UserData.class);
        return userData != null ? userData.getData() : null;
    }
}
