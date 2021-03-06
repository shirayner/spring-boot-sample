# SpringBoot 集成Quartz

[toc]



## 推荐阅读

> - [SpringBoot集成Quartz实现动态修改定时任务间隔](https://juejin.im/post/6844904003675881486)
> - [SpringBoot集成Quartz实现定时任务](https://segmentfault.com/a/1190000022552084)
> - [《SpringBoot整合Quartz实现定时任务》](https://www.jianshu.com/p/a99b80021be6)
> - [Spring Boot集成quartz实现定时任务并支持切换任务数据源](https://juejin.im/post/6844903926433579016)





## 一、Cron表达式

### 推荐阅读

> - [Cron表达式校验工具-bejson](https://www.bejson.com/othertools/cronvalidate/)
> - [cron表达式详解](https://www.cnblogs.com/linjiqin/p/3178452.html)

### 1. 语法格式

（1）Cron表达式是一个字符串， 以空格分隔，分为6或7个域:

> - Seconds Minutes Hours DayofMonth Month DayofWeek Year
> - Seconds Minutes Hours DayofMonth Month DayofWeek



（2）各域的含义如下：

| 域                       | 允许值                                 | 允许的特殊字符             |
| ------------------------ | -------------------------------------- | -------------------------- |
| 秒（Seconds）            | 0~59的整数                             | , - * /   四个字符         |
| 分（*Minutes*）          | 0~59的整数                             | , - * /   四个字符         |
| 小时（*Hours*）          | 0~23的整数                             | , - * /   四个字符         |
| 日期（*DayofMonth*）     | 1~31的整数（但是你需要考虑你月的天数） | ,- * ? / L W C   八个字符  |
| 月份（*Month*）          | 1~12的整数或者 JAN-DEC                 | , - * /   四个字符         |
| 星期（*DayofWeek*）      | 1~7的整数或者 SUN-SAT （1=SUN）        | , - * ? / L C #   八个字符 |
| 年(可选，留空)（*Year*） | 1970~2099                              | , - * /   四个字符         |



（3）域取值范围

每一个域都使用数字，但还可以出现如下特殊字符，它们的含义是：

| 域   | 描述                                                         | 示例                                                         |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| *    | 表示匹配该域的任意值                                         | 假如在Minutes域使用*, 即表示每分钟都会触发事件               |
| ?    | 表示不指定值，只能用在DayofMonth和DayofWeek两个域。当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？” | 例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, 其中最后一位只能用？，而不能使用`*`，如果使用`*`表示不管星期几都会触发，实际上并不是这样。 |
| -    | 表示范围                                                     | 例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次     |
| /    | 表示增量，也即从起始时间开始触发，然后每隔固定时间触发一次   | 例如在Minutes域使用5/20，则意味着第5、25、45分钟触发一次     |
| ,    | 表示列出枚举值                                               | 例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次   |
| L    | 表示最后，只能出现在DayofWeek和DayofMonth域。                | 如果在DayofWeek域使用5L,意味着在最后的一个星期四触发         |
| W    | 表示有效工作日(周一到周五)，只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件 | 例如：在 DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；如果5日在星期一到星期五中的一天，则就在5日触发。另外一点，W的最近寻找不会跨过月份 。 |
| LW   | 这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五 |                                                              |
| #    | 用于确定每个月第几个星期几，只能出现在DayofWeek域            | 例如在4#2，表示某月的第二个星期三                            |



### 2.常用表达式

| 表达式            | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| `0/5 * * * * ?`   | 每隔5秒，执行任务                                            |
| `0 0/5 * * * ?`   | 每隔5分钟，执行任务                                          |
| `0 0 0/5 * * ?`   | 每隔5小时，执行任务                                          |
| `0 30 13 * * ?`   | 每天13:30:00，执行任务。第4、6个域用`?`或`*`皆可             |
| `0 * 13 * * ?`    | 每天13:00:00-13:59:00期间内，每隔1分钟，执行任务             |
| `0 0 2 1 * ?`     | 每月的1日的凌晨2点，执行任务                                 |
| `0 15 10 ? * 2-6` | 每周一到周五10:15:00，执行任务。也可表示为``0 15 10 ? * MON-FRI`` |
| `0 15 10 L * ?`   | 每月最后一日的10:15:00，执行任务                             |
| `0 15 10 ? * 6L`  | 每月的最后一个星期五的10:15:00，执行任务                     |
| `0 15 10 ? * 6#3` | 每月的第三个星期五的10:15:00，执行任务                       |



注意：

（1）有些子表达式能包含一些范围或列表

> 例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT”
>
> “*”字符代表所有可能的值



























