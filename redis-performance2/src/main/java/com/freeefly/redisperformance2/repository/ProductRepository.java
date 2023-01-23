package com.freeefly.redisperformance2.repository;

import com.freeefly.redisperformance2.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
