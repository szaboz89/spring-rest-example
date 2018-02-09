package com.szabodev.example.rest.controller;

import com.szabodev.example.rest.domain.User;
import com.szabodev.example.rest.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String formPost(Model model, @RequestParam Integer limit) {
        List<User> users = apiService.getUsers(limit);
        model.addAttribute("users", users);
        return "users";
    }

//    @PostMapping("/users")
//    public String formPost(Model model, ServerWebExchange serverWebExchange) {
//        Mono<MultiValueMap<String, String>> formData = serverWebExchange.getFormData();
//        Mono<Integer> limit = formData.map(valueMap -> {
//            String value = valueMap.getFirst("limit");
//            return new Integer(value);
//        });
//        Flux<User> users = apiService.getUsers(limit);
//        model.addAttribute("users", users);
//        return "users";
//    }
}

