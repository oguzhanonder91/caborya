package com.common.service.impl;

import com.common.entity.PasswordResetToken;
import com.common.entity.User;
import com.common.repository.PasswordResetTokenRepository;
import com.common.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by oguzhanonder - 29.10.2018
 */
@Service
public class PasswordResetTokenImpl extends BaseServiceImpl<PasswordResetToken> implements PasswordResetTokenService{

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public PasswordResetToken findByUser(User user) {
        return passwordResetTokenRepository.findByUser(user);
    }

    @Override
    public Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now) {
        return passwordResetTokenRepository.findAllByExpiryDateLessThan(now);
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {
        passwordResetTokenRepository.deleteByExpiryDateLessThan(now);
    }

}
