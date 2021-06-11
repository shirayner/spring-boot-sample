package com.ray.study.smaple.sb.elasticsearch.resthighlevelclient.service;

import com.ray.study.smaple.sb.elasticsearch.resthighlevelclient.ElasticsearchRestHighLevelClientApplicationTest;
import com.ray.study.smaple.sb.elasticsearch.resthighlevelclient.contants.ElasticsearchConstant;
import com.ray.study.smaple.sb.elasticsearch.resthighlevelclient.model.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * PersonServiceTest
 *
 * @author ray
 * @date 2020/6/22
 */
@Disabled("Disabled until you can connect a elastic server")
public class PersonServiceTest extends ElasticsearchRestHighLevelClientApplicationTest {

    @Autowired
    private PersonService personService;

    /**
     * 测试创建索引
     */
    @Test
    public void createIndex() {
        personService.createIndex(ElasticsearchConstant.INDEX_NAME);
    }

    /**
     * 测试删除索引
     */
    @Test
    public void deleteIndex() {
        personService.deleteIndex(ElasticsearchConstant.INDEX_NAME);
    }

    /**
     * 测试新增
     */
    @Test
    public void insert() {
        List<Person> list = new ArrayList<>();
        list.add(Person.builder().age(11).birthday(new Date()).country("CN").id(1L).name("哈哈").remark("test1").build());
        list.add(Person.builder().age(22).birthday(new Date()).country("US").id(2L).name("hiahia").remark("test2").build());
        list.add(Person.builder().age(33).birthday(new Date()).country("ID").id(3L).name("呵呵").remark("test3").build());

        personService.insert(ElasticsearchConstant.INDEX_NAME, list);
    }

    /**
     * 测试更新
     */
    @Test
    public void update() {
        Person person = Person.builder().age(33).birthday(new Date()).country("ID_update").id(3L).name("呵呵update").remark("test3_update").build();
        List<Person> list = new ArrayList<>();
        list.add(person);
        personService.update(ElasticsearchConstant.INDEX_NAME, list);
    }

    /**
     * 测试删除
     */
    @Test
    public void delete() {
        personService.delete(ElasticsearchConstant.INDEX_NAME, Person.builder().id(1L).build());
    }

    /**
     * 测试查询
     */
    @Test
    public void searchList() {
        List<Person> personList = personService.searchList(ElasticsearchConstant.INDEX_NAME);
        System.out.println(personList);
    }

}