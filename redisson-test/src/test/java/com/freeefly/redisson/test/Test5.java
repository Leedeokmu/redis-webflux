package com.freeefly.redisson.test;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Test5 extends BaseTest {

    @Test
    void expireEventTest() throws InterruptedException {
        RBucketReactive<String> bucket = client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 5, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
            .doOnNext(System.out::println).then();
        Mono<Integer> event = bucket.addListener((ExpiredObjectListener) name -> {
            System.out.println("Expired : " + name);
        });

        StepVerifier.create(Flux.zip(set.concatWith(get), event))
            .verifyComplete();

        TimeUnit.SECONDS.sleep(6);
    }


}
