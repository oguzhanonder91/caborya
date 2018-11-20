package com.common.service;

import com.common.entity.PasswordResetToken;
import com.common.entity.User;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by oguzhanonder - 29.10.2018
 */

public interface PasswordResetTokenService extends BaseService<PasswordResetToken>{

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);
}
