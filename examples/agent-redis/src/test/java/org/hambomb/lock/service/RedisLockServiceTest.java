package org.hambomb.lock.service;

import org.hambomb.lock.AgentRedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AgentRedisApplication.class})
public class RedisLockServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLockServiceTest.class);

    @Autowired
    private RedisLockService redisLockService;

    @Test
    public void modifyPhone() {

        redisLockService.modifyPhone();
    }

    @Test
    public void modifyPhoneBy() {

        int count = 20;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        final List<Thread> threads = IntStream.range(0, count).mapToObj(x -> {
            Thread thread = new Thread(() -> {
                redisLockService.modifyPhone();

            });
            return thread;
        }).collect(Collectors.toList());

        threads.forEach(thread -> {
            countDownLatch.countDown();
            thread.start();
        });

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}