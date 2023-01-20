package com.freeefly.redisson.test;

import com.freeefly.redisson.test.dto.Student;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Test6 extends BaseTest {

    @Test
    void mapTest() throws InterruptedException {
        RMapReactive<String, String> map = client.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> name = map.put("name", "sam");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "atlanta");
        StepVerifier.create(Flux.zip(name, age, city))
            .verifyComplete();
    }


    @Test
    void mapTest2() {
        RMapReactive<String, String> map = client.getMap("user:1", StringCodec.INSTANCE);
        Map<String, String> javaMap = Map.of(
            "name", "jake",
            "age", "30",
            "city", "miami"
        );
        StepVerifier.create(map.putAll(javaMap))
            .verifyComplete();
    }

    @Test
    void objectMapTest() {
        RMapReactive<Integer, Student> map = client.getMap("users",
            new TypedJsonJacksonCodec(Integer.class,
                Student.class));
        Student student1 = new Student("sam", 10, "atlanta");
        Student student2 = new Student("jake", 20, "miami");

        StepVerifier.create(map.putAll(Map.of(1, student1, 2, student2)))
            .verifyComplete();
    }

    @Test
    void mapCacheTest() throws InterruptedException {
        RMapCacheReactive<Integer, Student> mapCache = client.getMapCache("users:cache",
            new TypedJsonJacksonCodec(Integer.class,
                Student.class));
        Student student1 = new Student("sam", 10, "atlanta");
        Student student2 = new Student("jake", 20, "miami");
        Mono<Student> st1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        Mono<Student> st2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(Flux.zip(st1, st2))
            .verifyComplete();

        TimeUnit.SECONDS.sleep(3);
        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        TimeUnit.SECONDS.sleep(3);
        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();
    }


}
