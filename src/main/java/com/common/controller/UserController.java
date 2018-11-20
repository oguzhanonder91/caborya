package com.common.controller;

import com.common.util.OnRegistrationCompleteEvent;
import com.common.dto.UserDto;
import com.common.entity.User;
import com.common.exception.BaseNotFoundException;
import com.common.service.UserService;
import com.common.util.CommonMethod;
import com.common.util.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by oguzhanonder - 18.10.2018
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserValidator userValidator;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Kullanıcı Bulunamadı-");
        return user.get();
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserDto userDto , HttpServletRequest request,BindingResult bindingResult) {
        userValidator.validate(userDto,bindingResult);
        if (bindingResult.hasErrors()) {
            return null;
        }
        User userRegistered = userService.registerNewUserAccount(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userRegistered, request.getLocale(), CommonMethod.getAppUrl(request)));
        return userRegistered;
    }

    @PostMapping("/login")
    @ResponseStatus
    public void login(@RequestBody UserDto userDto , HttpServletRequest request, BindingResult bindingResult) throws ServletException {
       request.login(userDto.getEmail(),userDto.getPassword());
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Kullanıcı Bulunamadı-");
        userService.delete(user.get());
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable String id) {
        Optional<User> oldUser = userService.findById(id);
        if (!oldUser.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Kullanıcı Bulunamadı-");
        return userService.update(user);
    }


}
