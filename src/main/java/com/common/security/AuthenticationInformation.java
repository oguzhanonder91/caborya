package com.common.security;

import org.springframework.security.core.Authentication;

/**
 * Created by oguzhanonder - 18.10.2018
 */
public interface AuthenticationInformation {
    Authentication getAuthentication();
}
