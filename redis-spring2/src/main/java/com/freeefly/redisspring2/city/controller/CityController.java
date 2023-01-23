package com.freeefly.redisspring2.city.controller;

import com.freeefly.redisspring2.city.dto.City;
import com.freeefly.redisspring2.city.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("city")
@RestController
public class CityController {

    private final CityService service;

    @GetMapping("{zip}")
    public Mono<City> getCity(@PathVariable("zip") String zipCode) {
        return service.getCity(zipCode);

    }


}
