package com.freeefly.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RListReactive;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Test7 extends BaseTest {
    @Test
    void listTest() {
        RListReactive<Long> list = client.getList("number-list", LongCodec.INSTANCE);
        Mono<Void> listAdd = Flux.range(1, 10)
            .map(Long::valueOf)
            .flatMap(list::add)
            .then();
        StepVerifier.create(listAdd)
            .verifyComplete();
        StepVerifier.create(list.size())
            .expectNext(10)
            .verifyComplete();
    }


}
