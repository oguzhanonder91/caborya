package com.common.service;

import com.common.entity.User;
import com.common.entity.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by oguzhanonder - 29.10.2018
 */
public interface VerificationTokenService extends BaseService<VerificationToken>{

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);
}
