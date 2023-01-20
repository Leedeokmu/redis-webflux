package com.freeefly.redisson.test;

import com.freeefly.redisson.test.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(Lifecycle.PER_CLASS)
public class BaseTest {
    private static RedissonConfig config = new RedissonConfig();
    protected RedissonReactiveClient client;
    @BeforeAll
    void setClient() {
        client = config.getReactiveClient();
    }
    @AfterAll
    void shutdown() {
        client.shutdown();
    }

}
