[toc]









# 前言

## 推荐阅读





# 一、SpringBoot 集成Mybatis

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

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

}
```



### 4.2 mapper

```java
// @Mapper
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

    @Insert("INSERT INTO user(username, password, email, age, create_time, update_time) VALUES(#{username}, #{password}, #{email}, #{age}, #{createTime}, #{updateTime} )")
    int insertByUser(User user);

    @Insert("INSERT INTO user(username, password, email, age) VALUES(#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Update("UPDATE user SET email=#{email} WHERE username=#{username}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);

}
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
        // 1.insert:  insert一条数据，并select出来验证
        String email = "tom@qq.com";
        String name = "tom";
        userMapper.insertBy(name, "password", email);
        User u = userMapper.findByUserame(name);
        assertThat(u.getEmail(), is(email));


        // 2.update:  update一条数据，并select出来验证
        String email2="tom1@qq.com";
        u.setEmail(email2);
        userMapper.update(u);
        u = userMapper.findByUserame(name);
        assertThat(u.getEmail(), is(email2));


        // 3.delete  删除这条数据，并select验证
        userMapper.delete(u.getId());
        u = userMapper.findByUserame(name);
        assertThat(u, is(nullValue()));
    }

    @Test
    public void insert() {
        // 1.insert:  insert一条数据，并select出来验证
        User user = new User();
        user.setUsername("tom");
        user.setPassword("password");
        user.setEmail("tom@qq.com");
        user.setAge(21);
        userMapper.insert(user);

        User u = userMapper.findByUserame("tom");

        assertThat(u.getAge(), is(21));
    }


    @Test
    public void insertByUser() {
        // 1.insert:  insert一条数据，并select出来验证
        User user = new User();
        user.setUsername("tom");
        user.setAge(21);
        user.setPassword("password");
        user.setEmail("tom@qq.com");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertByUser(user);

        User u = userMapper.findByUserame("tom");

        assertThat(u.getAge(), is(21));
    }

    @Test
    public void insertByMap() {
        // 1.insert:  insert一条数据，并select出来验证
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("password", "password");
        map.put("email", "tom@qq.com");
        map.put("age", 21);
        userMapper.insertByMap(map);

        User u = userMapper.findByUserame("tom");

        assertThat(u.getAge(), is(21));
    }


}
```











# 参考资料

> 1. [Spring Boot(六)：如何优雅的使用 Mybatis](http://www.ityouknow.com/springboot/2016/11/06/spring-boot-mybatis.html)
> 2. [Spring Boot 揭秘与实战（二） 数据存储篇 - MyBatis整合](http://blog.720ui.com/2016/springboot_02_data_mybatis/)
> 3. [一起来学SpringBoot | 第七篇：整合Mybatis](https://blog.battcn.com/2018/05/09/springboot/v2-orm-mybatis/)
> 4. 

