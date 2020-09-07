package com.ray.study.smaple.spring.aop;

import com.ray.study.smaple.spring.aop.advisor.TestBeanAspect;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SampleAopAutoConfigurationTest
 * {@link EnableAspectJAutoProxyTests}
 *
 * @author ray
 * @date 2020/9/7
 */
class SampleAopAutoConfigurationTest {

    @Test
    public void withCglibProxy() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigWithCglibProxy.class);

        aspectIsApplied(ctx);
        assertThat(AopUtils.isCglibProxy(ctx.getBean(TestBean.class))).isTrue();
    }

    private void aspectIsApplied(ApplicationContext ctx) {
        TestBean testBean = ctx.getBean(TestBean.class);
        testBean.testAop();
    }



    @EnableAspectJAutoProxy(proxyTargetClass = true)
    static class ConfigWithCglibProxy {
        @Bean
        public TestBeanAspect testBeanAspect(){
            return new TestBeanAspect();
        }

        @Bean
        public TestBean testBean(){
            return new TestBean();
        }
    }


}



class TestBean {

    private String testStr = "testStr";

    public void testAop() {
        // 被拦截的方法，简单打印
        System.out.println("I am the true aop bean");
    }
}

