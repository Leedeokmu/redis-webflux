package com.freeefly.redisperformance2.service;

import com.freeefly.redisperformance2.entity.Product;
import com.freeefly.redisperformance2.repository.ProductRepository;
import com.freeefly.redisperformance2.service.util.CacheTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductServiceV2 {
    private final CacheTemplate<Integer, Product> cacheTemplate;

    public Mono<Product> getProduct(int id) {
        return cacheTemplate.get(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return productMono.flatMap(p -> cacheTemplate.update(id, p));
    }

    public Mono<Product> deleteProduct(int id) {
        return cacheTemplate.delete(id);
    }
}
