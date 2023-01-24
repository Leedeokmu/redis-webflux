package com.freeefly.redisperformance2.service;

import com.freeefly.redisperformance2.entity.Product;
import com.freeefly.redisperformance2.repository.ProductRepository;
import io.netty.util.internal.ThreadLocalRandom;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
//@Service
public class DataSetupService implements CommandLineRunner {
    private final ProductRepository repository;
    private final R2dbcEntityTemplate entityTemplate;
    @Value("classpath:schema/schema.sql")
    private Resource resource;

    @Override
    public void run(String... args) throws Exception {
        String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(sql);
        Mono<Void> insert = Flux.range(1, 1000)
            .map(i -> new Product(null, "product" + i, ThreadLocalRandom.current().nextInt(1, 100)))
            .collectList()
            .flatMapMany(list -> this.repository.saveAll(list))
            .then();

        entityTemplate.getDatabaseClient()
            .sql(sql)
            .then()
            .then(insert)
            .doFinally(s -> System.out.println("data setup done " + s))
            .subscribe();
    }
}
