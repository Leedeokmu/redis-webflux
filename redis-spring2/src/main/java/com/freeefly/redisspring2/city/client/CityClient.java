package com.freeefly.redisspring2.city.client;

import com.freeefly.redisspring2.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

    private final WebClient webClient;

    public CityClient(@Value("${city.service.url}") final String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .build();
    }

    public Mono<City> getCity(final String zipCode) {
        return webClient.get()
            .uri("{zip}", zipCode)
            .retrieve()
            .bodyToMono(City.class)
            ;
    }
}
