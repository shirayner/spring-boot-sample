package com.ray.study.smaple.sb.common.controller;

import com.ray.study.smaple.sb.common.api.ResponseData;
import com.ray.study.smaple.sb.common.exception.BaseException;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * UserController
 *
 * @author ray
 * @date 2020/3/9
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    public ResponseData query(String username){
      if(StringUtils.isEmpty(username)){
          throw  new BaseException("username should not be null!", HttpStatus.NOT_ACCEPTABLE);
      }

      Map<String, Object> user = new HashMap<>();
      user.put("username", username);
      user.put("age", 22);
      user.put("email", "123@qq.com");
      return ResponseData.success(user);
    }



}
