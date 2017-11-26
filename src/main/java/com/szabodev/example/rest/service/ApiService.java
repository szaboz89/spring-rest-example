package com.szabodev.example.rest.service;

import com.szabodev.example.rest.domain.User;

import java.util.List;

public interface ApiService {

    List<User> getUsers(Integer limit);
}
