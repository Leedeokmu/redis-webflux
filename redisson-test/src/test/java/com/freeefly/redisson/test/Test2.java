package com.freeefly.redisson.test;

import com.freeefly.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Test2 extends BaseTest {

    @Test
    void keyValueAccessTest() {
        Student student = new Student("marshal", 10, "atlanta");
        RBucketReactive<Student> bucket1 = client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
        Mono<Void> set1 = bucket1.set(student);
        Mono<Void> then = bucket1.get()
            .doOnNext(System.out::println)
            .then();
        StepVerifier.create(set1.concatWith(then))
            .verifyComplete();
    }


}
