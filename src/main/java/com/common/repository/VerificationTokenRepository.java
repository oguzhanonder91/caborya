package com.common.repository;

import com.common.entity.User;
import com.common.entity.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by oguzhanonder - 29.10.2018
 */
@Repository
public interface VerificationTokenRepository extends BaseRepository<VerificationToken>{

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);
}
