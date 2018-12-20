package com.common.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by oguzhanonder - 18.10.2018
 */
@Entity
@Where(clause = "entity_state=1")
public class PasswordResetToken extends VerificationToken{

    public PasswordResetToken() {
    }

    public PasswordResetToken(final String token) {
        this.setToken(token);
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public PasswordResetToken(final String token, final User user) {
        super();

        this.setToken(token);
        this.setUser(user);
        this.setExpiryDate(calculateExpiryDate(EXPIRATION));
    }

}
