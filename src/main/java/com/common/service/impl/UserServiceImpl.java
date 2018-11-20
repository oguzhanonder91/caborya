package com.common.service.impl;

import com.common.dao.UserDao;
import com.common.dto.UserDto;
import com.common.entity.PasswordResetToken;
import com.common.entity.User;
import com.common.entity.VerificationToken;
import com.common.repository.UserRepository;
import com.common.repository.VerificationTokenRepository;
import com.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by oguzhanonder - 18.10.2018
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        User user = userDao.registerNewUserAccount(accountDto);
        return super.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return userDao.getVerificationToken(verificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String token) {
        return userDao.generateNewVerificationToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        userDao.createPasswordResetTokenForUser(user, token);
    }


    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return userDao.getPasswordResetToken(token);
    }


    @Override
    public void changeUserPassword(User user, String password) {
        userDao.changeUserPassword(user, password);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        return userDao.checkIfValidOldPassword(user, password);
    }

    @Override
    public String validateVerificationToken(String token) {
        return userDao.validateVerificationToken(token);
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return userDao.getUsersFromSessionRegistry();
    }

    @Override
    public void login(HttpServletRequest httpServletRequest,String user, String pass) {
        userDao.authWithAuthManager(httpServletRequest, user, pass);
    }
}

