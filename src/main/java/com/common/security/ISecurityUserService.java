package com.common.security;

/**
 * Created by oguzhanonder - 29.10.2018
 */
public interface ISecurityUserService {

    String validatePasswordResetToken(String id, String token);

}
