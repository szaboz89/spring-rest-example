package com.szabodev.example.rest.service;

import com.szabodev.example.rest.domain.User;
import com.szabodev.example.rest.domain.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

//import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiServiceImpl implements ApiService {

    private final String apiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public ApiServiceImpl(@Value("${api.url}") String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("limit", limit);
        UserData userData = restTemplate.getForObject(uriBuilder.toUriString(), UserData.class);
        return userData.getData();
    }

//    @Override
//    public Flux<User> getUsers(Mono<Integer> limit) {
//        return WebClient
//                .create(apiUrl)
//                .get()
//                .uri(uriBuilder -> uriBuilder.queryParam("limit", limit.block()).build())
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .flatMap(resp -> resp.bodyToMono(UserData.class))
//                .flatMapIterable(UserData::getData);
//    }
}
