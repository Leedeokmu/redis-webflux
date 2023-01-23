package com.freeefly.redisspring2.city.service;

import com.freeefly.redisspring2.city.client.CityClient;
import com.freeefly.redisspring2.city.dto.City;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CityService {

    private final CityClient cityClient;
    private final RMapReactive<String, City> cityMap;

    public CityService(CityClient cityClient, RedissonReactiveClient client) {
        this.cityClient = cityClient;
        this.cityMap = client.getMap("city",
            new TypedJsonJacksonCodec(String.class, City.class));
    }

    public Mono<City> getCity(final String zipCode) {
        return cityMap.get(zipCode)
            .switchIfEmpty(
                this.cityClient.getCity(zipCode)
                    .flatMap(city -> this.cityMap.fastPut(zipCode, city).thenReturn(city))
            );


    }


}
