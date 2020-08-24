package com.ray.study.smaple.sb.task.async.spring.task;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AsyncTaskTest
 *
 * @author ray
 * @date 2020/8/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class AsyncTaskTest {

    @Autowired
    private AsyncTask task;

    @Test
    void testAsyncTasks() throws Exception {
        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();
    }

}