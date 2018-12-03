package com.common.service.impl;

import com.common.entity.User;
import com.common.entity.VerificationToken;
import com.common.repository.VerificationTokenRepository;
import com.common.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by oguzhanonder - 29.10.2018
 */
@Service
public class VerificationTokenImpl extends BaseServiceImpl<VerificationToken> implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
        return verificationTokenRepository.findAllByExpiryDateLessThan(now);
    }

    @Override
    public void deleteByExpiryDateLessThan(Date now) {
        verificationTokenRepository.deleteByExpiryDateLessThan(now);
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        verificationTokenRepository.deleteAllExpiredSince(now);
    }
}
