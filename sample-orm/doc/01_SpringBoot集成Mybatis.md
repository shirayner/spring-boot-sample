[toc]









# 前言

## 推荐阅读





# 一、SpringBoot 集成Mybatis

## 1.数据库准备

创建数据库`spring-boot-sample` ，然后创建表`sys_user`

```sql
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT  COMMENT '主键自增',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `email`    varchar(50) NOT NULL COMMENT '邮箱',
  `age` int (3) unsigned DEFAULT 3 COMMENT '年龄',
  `creation_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建日期',
  `last_update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '上次更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
```



## 2.引入依赖

```xml
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
```



## 3.配置

### 3.1 `application.yml`

```yml
spring:
  datasource:       # 配置数据源
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/spring-boot-sample?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: root

mybatis:
  type-aliases-package: com.ray.study.smaple.sb.mybatis.basic.entity
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true 
```



### 3.2 启动类

在启动类中，通过`@MapperScan` 指定 mapper 扫描包

```java
@MapperScan("com.ray.study.smaple.sb.mybatis.basic.mapper")
@SpringBootApplication
public class MybatisBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisBasicApplication.class, args);
    }

}
```



> 若不想使用`@MapperScan` ，那就需要直接在每个 Mapper 类上面添加注解`@Mapper`，以此来指定扫描的mapper





## 4.业务实现

### 4.1 entity

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private Long id;

	private String username;

	private String password;

	private String email;

	private Integer age;

	private Date creationDate;

	private Date lastUpdateDate;

}

```



### 4.2 mapper

```java
public interface UserMapper {

    /**
     * 根据用户名查询用户结果集
     *
     * @param username 用户名
     * @return 查询结果
     */
    @Select("SELECT * FROM  user  WHERE username = #{username}")
    User findByUserame(@Param("username") String username);


    /**
     * 查询所有用户
     *
     * @return
     */
    @Results({@Result(property = "username", column = "username"), @Result(property = "age", column = "age")})
    @Select("SELECT username, age FROM user")
    List<User> findAll();

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 成功 1 失败 0
     */
    int insert(User user);

    @Insert("INSERT INTO user(username, password, email) VALUES(#{username}, #{password}, #{email})")
    int insertBy(@Param("username") String username, @Param("password") String password, @Param("email") String email);

    @Insert("INSERT INTO user(username, password, email, age, creation_date, last_update_date) VALUES(#{username}, #{password}, #{email}, #{age}, #{creationDate}, #{lastUpdateDate} )")
    int insertByUser(User user);

    @Insert("INSERT INTO user(username, password, email, age) VALUES(#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Update("UPDATE user SET email=#{email} WHERE username=#{username}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);

}
```















# 参考资料

> 1. [Spring Boot(六)：如何优雅的使用 Mybatis](http://www.ityouknow.com/springboot/2016/11/06/spring-boot-mybatis.html)
> 2. [Spring Boot 揭秘与实战（二） 数据存储篇 - MyBatis整合](http://blog.720ui.com/2016/springboot_02_data_mybatis/)
> 3. [一起来学SpringBoot | 第七篇：整合Mybatis](https://blog.battcn.com/2018/05/09/springboot/v2-orm-mybatis/)
> 4. 

