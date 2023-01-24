package com.freeefly.redisperformance2.service.util;

import reactor.core.publisher.Mono;

public abstract class CacheTemplate<KEY, ENTITY> {
    public Mono<ENTITY> get(KEY key) {
        return getFromCache(key)
            .switchIfEmpty(
                getFromSource(key)
                    .flatMap(e -> updateCache(key, e)) // result of updateCache
            );
    }

    public Mono<ENTITY> update(KEY key, ENTITY entity) {
        return updateSource(key, entity)
            .flatMap(e -> deleteCache(key).thenReturn(e)); // result of updateSource
    }

    public Mono<ENTITY> delete(KEY key) {
        return deleteSource(key)
            .then(deleteCache(key)); // result of deleteCache
    }


    abstract Mono<ENTITY> getFromSource(KEY key);
    abstract Mono<ENTITY> getFromCache(KEY key);
    abstract Mono<ENTITY> updateSource(KEY key, ENTITY entity);
    abstract Mono<ENTITY> updateCache(KEY key, ENTITY entity);
    abstract Mono<ENTITY> deleteSource(KEY key);
    abstract Mono<ENTITY> deleteCache(KEY key);
}
