package com.freeefly.redisson.test.config;

import static java.util.Objects.isNull;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

public class RedissonConfig {

    private RedissonClient redissonClient;

    public RedissonClient getClient() {
        if (isNull(redissonClient)) {
            Config config = new Config();
            config
                .useSingleServer()
                .setAddress("redis://localhost:6379")
            ;
            redissonClient = Redisson.create(config);
        }
        return redissonClient;
    }
    public RedissonReactiveClient getReactiveClient() {
        return getClient().reactive();
    }
}
