package com.common.job;

import com.common.service.PasswordResetTokenService;
import com.common.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * Created by oguzhanonder - 2.12.2018
 */

@Service
@Transactional
public class TokenPurgeJob {

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        passwordResetTokenService.deleteAllExpiredSince(now);
        verificationTokenService.deleteAllExpiredSince(now);
    }
}
