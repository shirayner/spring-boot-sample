[toc]







# 前言

## 推荐阅读





# 一、SpringBoot 集成SpringDataJpa

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
        <!-- jpa 相关依赖 start-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- jpa 相关依赖 end-->
```



## 3.配置

### 3.1 `application.yml`

主要配置下数据源与JPA

由于我们配置的`ddl-auto = update`，因此不需要创建相关数据表，只需要创建`spring-boot-sample`数据库即可

```yml
spring:
  datasource:       # 配置数据源
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/spring-boot-sample?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    username: root
    password: root
  jpa:                 # 配置jpa
    database: mysql       # 数据库类型
    show-sql: true        # 打印sql语句
    hibernate:
      ddl-auto: update    # 加载 Hibernate时， 自动更新数据库结构
```



数据源的配置类为`DataSourceProperties`，具体的默认配置:

```java
	// 默认配置见 determineDriverClassName 、 determineUsername、determineUrl、determinePassword
	public DataSourceBuilder<?> initializeDataSourceBuilder() {
		return DataSourceBuilder.create(getClassLoader()).type(getType())
				.driverClassName(determineDriverClassName()).url(determineUrl())
				.username(determineUsername()).password(determinePassword());
	}
```



JPA的配置类为`JpaProperties`、`HibernateProperties`



`Hibernate DDL`配置的含义：

> - **create：** 每次运行程序时，都会重新创建表，故而数据会丢失
> - **create-drop：** 每次运行程序时会先创建表结构，然后待程序结束时清空表
> - **upadte：** 每次运行程序，没有表时会创建表，如果对象发生改变会更新表结构，原有数据不会清空，只会更新（推荐使用）
> - **validate：** 运行程序会校验数据与数据库的字段类型是否相同，**字段不同会报错**



### 3.2 启动类

```java
@EnableJpaAuditing
@SpringBootApplication
public class SpringBootDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

}
```





## 4.业务实现

### 4.1 entity

（1）BaseEntity

```java
@Data
@MappedSuperclass  // 声明子类可继承基类字段
@EntityListeners(AuditingEntityListener.class)   // 监听实体类变更
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate  // 自动填充 CreatedDate
	private LocalDateTime createTime;

	@LastModifiedDate // 自动填充 LastModifiedDate
	private LocalDateTime updateTime;
}

```





（2）User

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@DynamicInsert
public class User extends BaseEntity{

    private String username;

    private String password;

    private String email;

    private Integer age;

}

```







### 4.2 repository

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 查询年龄大于等于指定年龄的用户
     * @param age 年龄
     * @return userList
     */
    List<User> findByAgeGreaterThanEqualOrderById(Integer age);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return userList
     */
    List<User> findAllByUsername(String username);


    /**
     * 根据用户名模糊查询
     * @param username username
     * @return userList
     */
    List<User> findByUsernameLike(String username);

    /**
     * 模糊查询
     * @param username
     * @return
     */
    List<User> findByUsernameContaining(String username);
}

```







## 5.测试类

```java
@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    /**
     * 查询用户
     */
    @Test
    public void queryUser(){

        List<User> userToAddList = new ArrayList<>();
        userToAddList.add(new User("aatomcat000","password", "aatomcat000@qq.com", 20));
        userToAddList.add(new User("tomcat","password", "tomcat@qq.com",21));
        userToAddList.add(new User("tttomcat","password", "tttomcat@qq.com",22));
        userToAddList.add(new User("tttomcat111","password", "tttomcat111@qq.com",23));
        userToAddList.add(new User("tomcataaa","password", "tomcataaa@qq.com",24));

        // 新增用户
        userToAddList.forEach(user -> {
            userRepository.save(user);
        });


        // 查询所有名字为tom的用户
        List<User> userList = userRepository.findAllByUsername("tom");
        // assertThat(userList.size(), is(1));
        System.out.println(userList);

        // 查询所有年龄大于等于 21 的用户
        List<User> userList2 = userRepository.findByAgeGreaterThanEqualOrderById(21);
        //assertThat(userList2.size(), is(notNullValue()));
        System.out.println(userList2);

        // 精确查询，这里的like 并不是模糊查询
        List<User> userList3 = userRepository.findByUsernameLike("tom");
        //assertThat(userList3.size(), is(notNullValue()));
        System.out.println(userList3);

        // 模糊查询: Containing才是模糊查询
        List<User> userList4 = userRepository.findByUsernameContaining("tom");
        //assertThat(userList4.size(), is(notNullValue()));
        System.out.println(userList4);
    }


    /**
     * 新增用户
     */
    @Test
    public void insertUser(){

        User user = new User();
        user.setUsername("tom");
        user.setPassword("password");
        user.setEmail("tom@qq.com");
        user.setAge(21);
        User user1 = userRepository.save(user);

        assertThat(user.getId(),is(notNullValue()));
    }


    /**
     * 更新用户
     */
    @Test
    public void updateUser(){

        User user = new User();
        user.setId(1L);
        user.setUsername("tom2");
        user.setAge(1212);
        User user1 = userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(1L);
        User updatedUser = null;
        if(optionalUser.isPresent()){
            updatedUser = optionalUser.get();
        }

        assertThat(updatedUser.getAge(),is(1212));
    }


    /**
     * 删除用户
     */
    @Test
    public void deleteUser(){
        User user = new User();
        user.setUsername("tom2");
        userRepository.delete(user);

        List<User> userList = userRepository.findAllByUsername("tom2");
        assertThat(userList, is(nullValue()));

    }

}
```















# 参考资料

> - [纯洁的微笑__Spring Boot(五)：Spring Boot Jpa 的使用](http://www.ityouknow.com/springboot/2016/08/20/spring-boot-jpa.html)
> - [唐亚峰__一起来学SpringBoot | 第六篇：整合SpringDataJpa](https://blog.battcn.com/2018/05/08/springboot/v2-orm-jpa/)
> - [SpringDataJpa：JpaRepository增删改查](https://blog.csdn.net/fly910905/article/details/78557110)
> - [Jpa配置实体类创建时间更新时间自动赋值，@CreateDate，@LastModifiedDate](https://blog.csdn.net/tianyaleixiaowu/article/details/77931903)
> - 
> - 











