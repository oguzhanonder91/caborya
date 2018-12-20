package com.common.security;

import com.common.dao.UserDao;
import com.common.entity.PasswordResetToken;
import com.common.entity.User;
import com.common.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * Created by oguzhanonder - 29.10.2018
 */

@Service
@Transactional
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private UserDao userDao;

    @Override
    public String validatePasswordResetToken(String id, String token) {
        PasswordResetToken passToken = passwordResetTokenService.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        User user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userDao.getAuthorities(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }



}
