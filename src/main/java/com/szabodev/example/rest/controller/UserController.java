package com.szabodev.example.rest.controller;

import com.szabodev.example.rest.domain.User;
import com.szabodev.example.rest.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class UserController {

    private final ApiService apiService;

    @Autowired
    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {
        Mono<MultiValueMap<String, String>> formData = serverWebExchange.getFormData();
        Mono<Integer> limit = formData.map(valueMap -> {
            String value = valueMap.getFirst("limit");
            return value != null ? new Integer(value) : new Integer(0);
        });
        Flux<User> users = apiService.getUsers(limit);
        model.addAttribute("users", users);
        return "users";
    }
}

