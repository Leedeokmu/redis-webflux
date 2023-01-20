package com.freeefly.redisson.test;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Test1 extends BaseTest{
    @Test
    void keyValueAccessTest(){
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam");
        Mono<Void> get = bucket.get()
            .doOnNext(System.out::println).then();
        StepVerifier.create(set.concatWith(get))
            .verifyComplete();
    }

    @Test
    void expireTest(){
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
            .doOnNext(System.out::println).then();
        StepVerifier.create(set.concatWith(get))
            .verifyComplete();
    }

    @Test
    void extendExpireTest() throws InterruptedException {
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
            .doOnNext(System.out::println).then();
        StepVerifier.create( set.concatWith(get))
            .verifyComplete();

        TimeUnit.SECONDS.sleep(5);
        Mono<Boolean> mono = bucket.expire(60, TimeUnit.SECONDS);
        StepVerifier.create(mono)
            .expectNext(true)
            .verifyComplete();

        Mono<Void> then = bucket.remainTimeToLive()
            .doOnNext(System.out::println)
            .then();
        StepVerifier.create(then)
            .verifyComplete();
    }

}
