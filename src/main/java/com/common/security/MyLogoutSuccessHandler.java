package com.common.security;

import com.common.entity.User;
import com.common.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oguzhanonder - 29.10.2018
 */
@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private static  final Logger LOGGER = LoggerFactory.getLogger(MyLogoutSuccessHandler.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
         HttpSession session = request.getSession();

         String email = ((User)authentication.getPrincipal()).getEmail();
         LOGGER.info("Logout Successful with Principal: " + email);

        if (session != null) {
            session.removeAttribute("user");
            User userLogin = userService.findByEmail(email);
            userLogin.setLastLogoutTime(new Date(System.currentTimeMillis()));
            userService.update(userLogin);

            Map<String, String> messageMap = new HashMap<String, String>();
            messageMap.put("message", "Successfully logged out");
            PrintWriter writer = response.getWriter();
            mapper.writeValue(writer, messageMap);
        }


    }
}
