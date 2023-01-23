package com.freeefly.redisperformance2.service;

import com.freeefly.redisperformance2.entity.Product;
import com.freeefly.redisperformance2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository repository;

    public Mono<Product> getProduct(int id) {
        return repository.findById(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return repository.findById(id)
            .flatMap(product -> productMono.doOnNext(product1 -> product1.setId(id)))
            .flatMap(repository::save);

    }


}
