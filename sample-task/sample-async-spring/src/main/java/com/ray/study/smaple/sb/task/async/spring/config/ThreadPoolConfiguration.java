package com.ray.study.smaple.sb.task.async.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * AsyncThreadPoolConfig
 *  Spring 中线程池的自动配置见 TaskExecutionAutoConfiguration，一般需要根据实际情况自定义
 *
 * @author ray
 * @date 2020/8/24
 */
@Configuration
public class ThreadPoolConfiguration {

    /** 获取当前机器CPU数量 */
    private static final int cpu = Runtime.getRuntime().availableProcessors();
    /** 核心线程数（默认线程数） */
    private static final int corePoolSize = cpu;
    /** 最大线程数 **/
    private static final int maxPoolSize = cpu * 2;
    /** 允许线程空闲时间（单位：默认为秒） */
    private static final int keepAliveTime = 60;
    /** 缓冲队列数 **/
    private static final int queueCapacity = 200;
    /** 线程池名称前缀 */
    private static final String threadNamePrefix = "taskExecutor-";

    @Bean("taskExecutor") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //executor.setTaskDecorator(new ContextCopyingDecorator());
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置 线程池关闭 的时候 等待 所有任务都完成后，再继续 销毁 其他的 Bean，这样这些 异步任务 的 销毁 就会先于 数据库连接池对象 的销毁。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中 任务的等待时间，如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        // 初始化
        executor.initialize();
        return executor;
    }

}
