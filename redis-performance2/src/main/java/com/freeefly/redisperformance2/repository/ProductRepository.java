package com.freeefly.redisperformance2.repository;

import com.freeefly.redisperformance2.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
