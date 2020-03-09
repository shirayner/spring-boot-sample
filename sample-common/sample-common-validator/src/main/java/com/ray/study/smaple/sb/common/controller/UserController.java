package com.ray.study.smaple.sb.common.controller;

import com.ray.study.smaple.sb.common.dto.UserDto;
import com.ray.study.smaple.sb.common.validate.Groups;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * UserController
 *
 * @author ray
 * @date 2020/3/9
 */
@RestController
@RequestMapping("user")
public class UserController {

    private static Map<String, UserDto> userDtoMap = new ConcurrentHashMap<>();

    @GetMapping
    public List<UserDto> list(){
        return new ArrayList<>(userDtoMap.values());
    }

    @PostMapping
    public UserDto create(@RequestBody @Validated(value = Groups.Default.class) UserDto user) {
        String userId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        user.setId(userId);
        userDtoMap.putIfAbsent(user.getUsername(), user);
        return user;
    }

    @PutMapping
    public UserDto update(@RequestBody @Validated(value = {Groups.Default.class, Groups.Update.class})  UserDto user) {
        userDtoMap.putIfAbsent(user.getUsername(), user);
        return user;
    }


}
