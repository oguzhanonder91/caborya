package com.common.controller;

import com.common.dto.PasswordDto;
import com.common.entity.VerificationToken;
import com.common.exception.BaseServerException;
import com.common.util.*;
import com.common.dto.UserDto;
import com.common.entity.User;
import com.common.exception.BaseNotFoundException;
import com.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
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
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CommonMethod commonMethod;

    @Autowired
    private AuthenticationInformation information;

    @Autowired
    private ISecurityUserService securityUserService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(Locale locale,@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent())
            throw new BaseNotFoundException(id + messages.getMessage("message.resendToken", null, locale));
        return user.get();
    }

    @PostMapping("/registration")
    @ResponseBody
    public User create(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
        User userRegistered = userService.registerNewUserAccount(userDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userRegistered, request.getLocale(), commonMethod.getAppUrl(request)));
        return userRegistered;
    }


    // Menu - Account only update password
    @PutMapping(value = "/updatePassword")
    public String changeUserPassword(Locale locale, @Valid PasswordDto passwordDto) {
        Authentication authentication = information.getAuthentication();
        User user = userService.findByEmail(((User) authentication.getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new BaseServerException(messages.getMessage("PasswordMatches.user",null,locale));
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return messages.getMessage("message.updatePasswordSuc", null, locale);
    }

    // forget password
    @PutMapping(value = "/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request, @RequestParam("email")  String userEmail) {
         User user = userService.findByEmail(userEmail);
        if (user != null) {
             String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(commonMethod.constructResetTokenEmail(commonMethod.getAppUrl(request), request.getLocale(), token, user));
        }
        return new ResponseEntity<>(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()),HttpStatus.OK);
    }

    @GetMapping(value = "/changePassword/{userOid}/{token}")
    public String showChangePasswordPage(Locale locale, Model model, @RequestParam("userOid")  String oid, @RequestParam("token") String token) {
        String result = securityUserService.validatePasswordResetToken(oid, token);
        if (result != null) {
            model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }


    @PutMapping(value = "/savePassword")
    @ResponseBody
    public ResponseEntity<String> savePassword(Locale locale, @Valid PasswordDto passwordDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new ResponseEntity<>(messages.getMessage("message.resetPasswordSuc", null, locale),HttpStatus.OK);
    }


    @GetMapping("/registrationConfirm/{token}")
    public String confirmRegistration(@PathVariable String token) {
        String result = userService.validateVerificationToken(token);
        return result;
    }

    @GetMapping("/resendRegistrationToken/{token}")
    public ResponseEntity<String> resendRegistrationToken(HttpServletRequest request, @PathVariable("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        mailSender.send(commonMethod.constructResendVerificationTokenEmail(commonMethod.getAppUrl(request), request.getLocale(), newToken, user));
        return new ResponseEntity<>(messages.getMessage("message.resendToken", null, request.getLocale()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(Locale locale,@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent())
            throw new BaseNotFoundException(id + messages.getMessage("message.resendToken", null, locale));
        userService.delete(user.get());
    }

    @PutMapping("/{id}")
    public User updateUser(Locale locale,@RequestBody User user, @PathVariable String id) {
        Optional<User> oldUser = userService.findById(id);
        if (!oldUser.isPresent())
            throw new BaseNotFoundException(id + messages.getMessage("message.resendToken", null, locale));
        return userService.update(user);
    }


}
