package com.freeefly.redisson.test;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RPatternTopicReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Test8 extends BaseTest{

    @Test
    void pubsubTest() throws InterruptedException {
        RPatternTopicReactive patternTopic = client.getPatternTopic("slack-room*",
            StringCodec.INSTANCE);
        patternTopic.addListener(String.class,
            (pattern, topic, msg) -> {
                System.out.println(String.format("%s : %s : %s\n", pattern, topic, msg));
            }).subscribe();
        TimeUnit.SECONDS.sleep(60);
    }


}
