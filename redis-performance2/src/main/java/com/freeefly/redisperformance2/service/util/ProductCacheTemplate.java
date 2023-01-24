package com.freeefly.redisperformance2.service.util;

import com.freeefly.redisperformance2.entity.Product;
import com.freeefly.redisperformance2.repository.ProductRepository;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductCacheTemplate extends CacheTemplate<Integer, Product> {
    private final ProductRepository repository;
    private final RMapReactive<Integer, Product> map;
    public ProductCacheTemplate(ProductRepository repository, RedissonReactiveClient client) {
        this.repository = repository;
        map = client.getMap("product",
            new TypedJsonJacksonCodec(Integer.class, Product.class));
    }

    @Override
    Mono<Product> getFromSource(Integer id) {
        return repository.findById(id);
    }

    @Override
    Mono<Product> getFromCache(Integer id) {
        return map.get(id);
    }

    @Override
    Mono<Product> updateSource(Integer id, Product product) {
        return repository.findById(id)
            .doOnNext(p -> product.setId(id))
            .flatMap(p -> repository.save(product));
    }

    @Override
    Mono<Product> updateCache(Integer id, Product product) {
        return map.fastPut(id, product).thenReturn(product);
    }

    @Override
    Mono<Product> deleteSource(Integer id) {
        return repository.findById(id)
            .flatMap(e -> repository.deleteById(id).thenReturn(e));
    }

    @Override
    Mono<Product> deleteCache(Integer id) {
        return map.get(id)
            .flatMap(e -> map.fastRemove(id).thenReturn(e));
    }
}
