# Spring @Async异步任务

[toc]

## 推荐阅读

> - [SpringBoot @Async异步并行执行任务](https://blog.csdn.net/zhuyu19911016520/article/details/99992668)
> - [如何在 Spring 异步调用中传递上下文](https://juejin.im/post/6844903904539312141#heading-9)
> - [Spring Boot @Async 异步任务执行](https://www.cnblogs.com/cjsblog/p/9016657.html)
> - [实战Spring Boot 2.0系列(三) - 使用@Async进行异步调用详解](https://juejin.im/post/6844903621822251021)



## 一、Spring @Async 异步任务

### 1.环境准备



#### 1.1 引入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```





#### 1.2 启动类

配置 `@EnableAsync` 注解开启异步处理

```java
@SpringBootApplication
@EnableAsync
public class SpringAsyncTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAsyncTaskApplication.class, args);
    }
}

```



#### 1.3 任务抽象类

创建任务抽象类 `AbstractTask`，并分别配置三个任务方法 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()`。

```java
public abstract class AbstractTask {

    private static Random random = new Random();

    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }
}

```



### 2.同步调用

下面通过一个简单示例来直观的理解什么是同步调用：

（1）定义 `Task` 类，继承 `AbstractTask`，三个处理函数分别模拟三个执行任务的操作，操作消耗时间随机取（`10` 秒内）。

```java
@Component
public class Task extends AbstractTask {
}
```



（2）在 **单元测试** 用例中，注入 `Task` 对象，并在测试用例中执行 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()` 三个方法。

```java
@RunWith(SpringRunner.class)
@SpringBootTest
class TaskTest {

    @Autowired
    private Task task;

    @Test
    public void testSyncTasks() throws Exception {
        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();
    }
}
```

（3）执行单元测试，可以看到类似如下输出：

```log
开始做任务一
完成任务一，耗时：4059毫秒
开始做任务二
完成任务二，耗时：6316毫秒
开始做任务三
完成任务三，耗时：1973毫秒
```

任务一、任务二、任务三顺序的执行完了，换言之 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()` 三个方法顺序的执行完成。



### 3.异步调用

上述的 **同步调用** 虽然顺利的执行完了三个任务，但是可以看到 **执行时间比较长**，若这三个任务本身之间 **不存在依赖关系**，可以 **并发执行** 的话，同步调用在 **执行效率** 方面就比较差，可以考虑通过 **异步调用** 的方式来 **并发执行**。

（1）创建 `AsyncTask`类，分别在方法上配置 `@Async` 注解，将原来的 **同步方法** 变为 **异步方法**。

```java
@Component
public class AsyncTask extends AbstractTask {

    @Async
    public void doTaskOne() throws Exception {
        super.doTaskOne();
    }

    @Async
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
    }

    @Async
    public void doTaskThree() throws Exception {
        super.doTaskThree();
    }
    
}
```



（2）在 **单元测试** 用例中，注入 `AsyncTask` 对象，并在测试用例中执行 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()` 三个方法。

```java
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
```



（3）执行单元测试，可以看到类似如下输出：

```
开始做任务三
开始做任务一
开始做任务二
```



如果反复执行单元测试，可能会遇到各种不同的结果，比如：

> 1. 没有任何任务相关的输出
> 2. 有部分任务相关的输出
> 3. 乱序的任务相关的输出

原因是目前 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()` 这三个方法已经 **异步执行** 了。主程序在 **异步调用** 之后，主程序并不会理会这三个函数是否执行完成了，由于没有其他需要执行的内容，所以程序就 **自动结束** 了，导致了 **不完整** 或是 **没有输出任务** 相关内容的情况。

> 注意：@Async所修饰的函数不要定义为static类型，这样异步调用不会生效。

### 4.异步回调

为了让 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()` 能正常结束，假设我们需要统计一下三个任务 **并发执行** 共耗时多少，这就需要等到上述三个函数都完成动用之后记录时间，并计算结果。

那么我们如何判断上述三个 **异步调用** 是否已经执行完成呢？我们需要使用 `Future<T>` 来返回 **异步调用** 的 **结果**。


（1）创建 `AsyncCallBackTask` 类，声明 `doTaskOneCallback()`，`doTaskTwoCallback()`，`doTaskThreeCallback()` 三个方法，对原有的三个方法进行包装。

```java
@Component
public class AsyncCallBackTask extends AbstractTask {
   
    @Async
    public Future<String> doTaskOneCallback() throws Exception {
        super.doTaskOne();
        return new AsyncResult<>("任务一完成");
    }

    @Async
    public Future<String> doTaskTwoCallback() throws Exception {
        super.doTaskTwo();
        return new AsyncResult<>("任务二完成");
    }

    @Async
    public Future<String> doTaskThreeCallback() throws Exception {
        super.doTaskThree();
        return new AsyncResult<>("任务三完成");
    }
}
```



（2）在 **单元测试** 用例中，注入 `AsyncCallBackTask` 对象，并在测试用例中执行 `doTaskOneCallback()`，`doTaskTwoCallback()`，`doTaskThreeCallback()` 三个方法。循环调用 `Future` 的 `isDone()` 方法等待三个 **并发任务** 执行完成，记录最终执行时间。

```java
@RunWith(SpringRunner.class)
@SpringBootTest
class AsyncCallBackTaskTest {

    @Autowired
    private AsyncCallBackTask task;

    @Test
    public void testAsyncCallbackTask() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = task.doTaskOneCallback();
        Future<String> task2 = task.doTaskTwoCallback();
        Future<String> task3 = task.doTaskThreeCallback();

        // 三个任务都调用完成，退出循环等待
        while (!task1.isDone() || !task2.isDone() || !task3.isDone()) {
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}
```

看看都做了哪些改变：

> - 在测试用例一开始记录开始时间；
> - 在调用三个异步函数的时候，返回Future类型的结果对象；
> - 在调用完三个异步函数之后，开启一个循环，根据返回的Future对象来判断三个异步函数是否都结束了。若都结束，就结束循环；若没有都结束，就等1秒后再判断。
> - 跳出循环之后，根据结束时间 - 开始时间，计算出三个任务并发执行的总耗时。



（3）执行一下上述的单元测试，可以看到如下结果：

```
开始做任务一
开始做任务三
开始做任务二
完成任务二，耗时：4882毫秒
完成任务三，耗时：6484毫秒
完成任务一，耗时：8748毫秒
任务全部完成，总耗时：9043毫秒
复制代码
```

可以看到，通过 **异步调用**，让任务一、任务二、任务三 **并发执行**，有效的 **减少** 了程序的 **运行总时间**。



### 5.使用线程池

（1）在上述操作中，创建一个 **线程池配置类** `ThreadPoolConfiguration` ，并配置一个 **任务线程池对象** `taskExecutor`。

```java
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
        // 初始化
        executor.initialize();
        return executor;
    }

}

```

（2）创建 `AsyncExecutorTask`类，三个任务的配置和 `AsyncTask` 一样，不同的是 `@Async` 注解需要指定前面配置的 **线程池的名称** `taskExecutor`。

```java
@Component
public class AsyncExecutorTask extends AbstractTask {

    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        super.doTaskOne();
        out.println("任务一，当前线程：" + currentThread().getName());
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
        out.println("任务二，当前线程：" + currentThread().getName());
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        super.doTaskThree();
        out.println("任务三，当前线程：" + currentThread().getName());
    }
}
```



（3）在 **单元测试** 用例中，注入 `AsyncExecutorTask` 对象，并在测试用例中执行 `doTaskOne()`，`doTaskTwo()`，`doTaskThree()` 三个方法。

```java
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
```





### 6. 优雅地关闭线程池

> 由于在应用关闭的时候异步任务还在执行，导致类似 **数据库连接池** 这样的对象一并被 **销毁了**，当 **异步任务** 中对 **数据库** 进行操作就会出错。

解决方案如下:

> 重新设置线程池配置对象，新增线程池 `setWaitForTasksToCompleteOnShutdown()` 和 `setAwaitTerminationSeconds()` 配置：

```java
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

```



> - `setWaitForTasksToCompleteOnShutdown(true)`: 该方法用来设置 **线程池关闭** 的时候 **等待** 所有任务都完成后，再继续 **销毁** 其他的 `Bean`，这样这些 **异步任务** 的 **销毁** 就会先于 **数据库连接池对象** 的销毁。
>
> - `setAwaitTerminationSeconds(60)`: 该方法用来设置线程池中 **任务的等待时间**，如果超过这个时间还没有销毁就 **强制销毁**，以确保应用最后能够被关闭，而不是阻塞住。



### 7.线程上下文信息传递

参见：

> - [如何在 Spring 异步调用中传递上下文](https://juejin.im/post/6844903904539312141#heading-10)





