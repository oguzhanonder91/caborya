package com.common.dao;

import com.common.dto.UserDto;
import com.common.entity.PasswordResetToken;
import com.common.entity.Role;
import com.common.entity.User;
import com.common.entity.VerificationToken;
import com.common.exception.BaseException;
import com.common.service.PasswordResetTokenService;
import com.common.service.RoleService;
import com.common.service.UserService;
import com.common.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by oguzhanonder - 18.10.2018
 */
@Component
public class UserDao {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";


    public User registerNewUserAccount(UserDto accountDto) {
        if (emailExist(accountDto.getEmail())) {
            throw new BaseException("There is an account with that email adress: " + accountDto.getEmail());
        }
        User user = new User();

        user.setName(accountDto.getName());
        user.setSurname(accountDto.getSurname());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRoles(Arrays.asList(roleService.findByCode(accountDto.getRoleCode())));
        return user;
    }

    public User getUser(String verificationToken) {
        VerificationToken token = verificationTokenService.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }


    private boolean emailExist(String email) {
        return userService.findByEmail(email) != null;
    }

    public void deleteUser(User user) {
        VerificationToken verificationToken = verificationTokenService.findByUser(user);

        if (verificationToken != null) {
            verificationTokenService.delete(verificationToken);
        }

        PasswordResetToken passwordToken = passwordResetTokenService.findByUser(user);

        if (passwordToken != null) {
            passwordResetTokenService.delete(passwordToken);
        }

        userService.delete(user);
    }

    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = verificationTokenService.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = verificationTokenService.save(vToken);
        return vToken;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenService.save(myToken);
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenService.findByToken(token);
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return  verificationTokenService.findByToken(VerificationToken);
    }

    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime()
                - cal.getTime()
                .getTime()) <= 0) {
            verificationTokenService.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        userService.update(user);
        return TOKEN_VALID;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(roles);
    }

    public List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorities;
    }

}
