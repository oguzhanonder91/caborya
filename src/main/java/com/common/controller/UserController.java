package com.common.controller;

import com.common.dto.PasswordDto;
import com.common.entity.VerificationToken;
import com.common.exception.BaseServerException;
import com.common.util.*;
import com.common.dto.UserDto;
import com.common.entity.User;
import com.common.exception.BaseNotFoundException;
import com.common.service.UserService;
import com.common.util.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;


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

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CommonMethod commonMethod;


    @Autowired
    private AuthenticationInformation information;

    @GetMapping
    public List<User> findAll() {
        information.getAuthentication();
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
    public User create(@Valid UserDto userDto, HttpServletRequest request, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return null;
        }
        User userRegistered = userService.registerNewUserAccount(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userRegistered, request.getLocale(), commonMethod.getAppUrl(request)));
        return userRegistered;
    }

    @PostMapping(value = "/user/updatePassword")
    public String changeUserPassword(Locale locale, @Valid PasswordDto passwordDto) {
        Authentication authentication = information.getAuthentication();
        User user = userService.findByEmail(((User) authentication.getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new BaseServerException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return messages.getMessage("message.updatePasswordSuc", null, locale);
    }

    @GetMapping("/registrationConfirm/{token}")
    public String confirmRegistration(@PathVariable String token) {
        String result = userService.validateVerificationToken(token);
        return result;
    }

    @GetMapping("/user/resendRegistrationToken/{token}")
    public String resendRegistrationToken(HttpServletRequest request, @PathVariable("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        mailSender.send(commonMethod.constructResendVerificationTokenEmail(commonMethod.getAppUrl(request), request.getLocale(), newToken, user));
        return messages.getMessage("message.resendToken", null, request.getLocale());
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
