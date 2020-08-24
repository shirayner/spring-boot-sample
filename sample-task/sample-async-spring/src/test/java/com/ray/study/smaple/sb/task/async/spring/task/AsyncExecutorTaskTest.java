package com.ray.study.smaple.sb.task.async.spring.task;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AsyncExecutorTaskTest
 *
 * @author ray
 * @date 2020/8/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class AsyncExecutorTaskTest {
    @Autowired
    private AsyncExecutorTask task;

    @Test
    public void testAsyncExecutorTask() throws Exception {
        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();

        Thread.sleep(30 * 1000L);
    }

}