package com.common.repository;

import com.common.entity.PasswordResetToken;
import com.common.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by oguzhanonder - 29.10.2018
 */
@Repository
public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken>{

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);
}