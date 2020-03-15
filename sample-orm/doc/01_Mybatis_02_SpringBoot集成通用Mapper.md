[toc]









# 前言

## 推荐阅读





# 一、SpringBoot 集成通用Mapper 与分页插件

## 1.数据库准备

创建数据库`spring-boot-sample` ，然后创建表`user`

```sql
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '主键自增',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `email`    varchar(50) NOT NULL COMMENT '邮箱',
  `age` int (3) unsigned DEFAULT 3 COMMENT '年龄',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
```



## 2.引入依赖

在 `pom.xml` 中添加通用Mapper与分页插件的依赖包

```xml
        <!-- tk-mybatis 相关依赖 start-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.0.2</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.5</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- tk-mybatis 相关依赖 end-->
```



## 3.配置

### 3.1 `application.yml`

```yml
# 数据源配置
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/spring-boot-sample?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: root

# Mybatis 自身配置
mybatis:
  type-aliases-package: com.ray.study.smaple.sb.mybatis.tk.entity
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true


# 通用Mapper
mapper:
  identity: MYSQL
  mappers: tk.mybatis.mapper.common.BaseMapper
  not-empty: true
  enum-as-simple-type: true

# 分页插件
pagehelper:
  helper-dialect: mysql
  params: count=countSql
  reasonable: false
  support-methods-arguments: true
```





（1）配置属性的来源

Mybatis以及通用Mapper的可选配置项可见`tk.mybatis：mapper-spring-boot-autoconfigure`工程下的`spring-configuration-metadata.json`文件，在这个json文件中，我们可以看到有哪些可选配置项，配置项的描述，以及配置项是对应那个Java类的哪个属性

通用Mapper的配置主要集中在`tk.mybatis.mapper.entity.Config`类中，注释很详细。



（2）部分配置的说明

以下对上述配置中出现的部分配置给出说明

```properties
mapper.identity=MYSQL   # 主键自增回写方法,默认值MYSQL,详细说明请看文档
mapper.mappers=tk.mybatis.mapper.common.BaseMapper  # 通用Mapper接口,其他Mapper接口需要继承此接口
mapper.not-empty=true   # 设置 insert 和 update 中，是否判断字符串类型!=''
mapper.enum-as-simple-type=true # 枚举按简单类型处理

pagehelper.reasonable=true # 分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。

support-methods-argument=true  # 支持通过 Mapper 接口参数来传递分页参数，默认值false，分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，查找到合适的值时就会自动分页。

```



## 4.业务实现

### 4.1 entity

`通用Mapper`采用了JPA规范包中的注解，这种的设计避免了重复造轮子，更是让`Spring Data Jpa`的应用可以轻松切换到`Mybatis



```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String email;

	private Integer age;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

}

```



### 4.2 mapper

继承 `BaseMapper` 就可以了，这点有点类似 `JpaRepository`，同时也可以根据自己需要扩展出更适合自己项目的`BaseMapper`

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return  user
     */
    User findByUsername(String username);

}
```



UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.ray.study.smaple.sb.mybatis.tk.mapper.UserMapper">

  <select id="findByUsername" resultType="com.ray.study.smaple.sb.mybatis.tk.entity.User">
         select * from user u  where u.username =#{username}
  </select>

</mapper>
```





## 5.测试类

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        //1. 测试插入，主键回写
        for(int i = 0; i < 30; i++) {
            User user = new User();
            user.setUsername("tom"+i);
            user.setEmail("tom"+i+"@qq.com");
            user.setPassword("password");
            user.setAge(20+i);

            userMapper.insertSelective(user);
            assertThat(user.getId(), is(notNullValue()));
        }

        //2. 测试手写sql
        User  user0 =userMapper.findByUsername("tom0");
        assertThat(user0, is(notNullValue()));


        // 3.分页+排序
        int pageSize =10;
        // 3.1 lambda 写法
        final PageInfo<User> pageInfo = PageHelper
                .startPage(1, pageSize)
                .setOrderBy("id desc")
                .doSelectPageInfo(() -> this.userMapper.selectAll());
        assertThat(pageInfo.getSize(), lessThanOrEqualTo(pageSize));

        // 3.2 普通写法
        PageHelper.startPage(1, pageSize).setOrderBy("id desc");
        List<User> userList = userMapper.selectAll();
        final PageInfo<User> userPageInfo = new PageInfo<>(userList);
        assertThat(userPageInfo.getSize(), lessThanOrEqualTo(pageSize));

    }
}
```











# 参考资料

> 1. [abel533__Spring Boot 集成 MyBatis, 分页插件 PageHelper, 通用 Mapper](https://github.com/abel533/MyBatis-Spring-Boot)
> 2. [唐亚峰__一起来学SpringBoot | 第八篇：通用Mapper与分页插件的集成](https://blog.battcn.com/2018/05/10/springboot/v2-orm-mybatis-plugin/)

