package com.ray.study.smaple.mq.rabbit;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * RabbitMqProducerApplicationTest
 *
 * @author ray
 * @date 2020/3/26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled("remove this when you can connect to a mq server")
class RabbitMqApplicationTest {

    @Test
    public void contextLoads() {
    }

}