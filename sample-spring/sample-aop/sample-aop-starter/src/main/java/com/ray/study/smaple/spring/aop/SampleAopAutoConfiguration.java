package com.ray.study.smaple.spring.aop;

import com.ray.study.smaple.spring.aop.advisor.TestBeanAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * SampleAopAutoConfiguration
 * {@link EnableAspectJAutoProxy}
 *
 * @author ray
 * @date 2020/9/7
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SampleAopAutoConfiguration {

    @Bean
    public TestBeanAspect testBeanAspect(){
        return new TestBeanAspect();
    }

}
