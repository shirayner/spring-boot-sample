package com.ray.study.smaple.spring.mvc.controller;

import com.ray.study.smaple.spring.mvc.entity.User;
import com.ray.study.smaple.spring.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description
 *
 * @author r.shi 2020/04/02 10:41
 */
@RestController
@RequestMapping("/user")
public class RequestParamController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity list(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user){
        User save = userRepository.save(user);
        return ResponseEntity.ok(save);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody User user){
        User save = userRepository.save(user);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody User user){
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity queryById(@PathVariable("id") Long id){
        return ResponseEntity.ok( userRepository.findById(id));
    }

    @GetMapping("/query1")
    public ResponseEntity query1(User user,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNo){
        Example<User> userExample = Example.of(user);
        List<User> all = userRepository.findAll(userExample);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/query2")
    public ResponseEntity query2(@RequestParam String username, @RequestParam String password){
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        Example<User> userExample = Example.of(u);
        List<User> all = userRepository.findAll(userExample);
        return ResponseEntity.ok(all);
    }

}
