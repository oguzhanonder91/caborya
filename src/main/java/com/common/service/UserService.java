package com.common.service;

import com.common.dto.UserDto;
import com.common.entity.PasswordResetToken;
import com.common.entity.User;
import com.common.entity.VerificationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by oguzhanonder - 18.10.2018
 */
public interface UserService extends BaseService<User>{
    User findByEmail(String email);

    User registerNewUserAccount(UserDto accountDto);

    void deleteUser(User user);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    User getUser(String verificationToken);

}
