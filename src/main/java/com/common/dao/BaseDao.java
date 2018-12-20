package com.common.dao;

import com.common.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by oguzhanonder - 20.11.2018
 */
@Component
public class BaseDao {

    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "anonymous";
        }

        return ((User) authentication.getPrincipal()).getId();
    }
}
