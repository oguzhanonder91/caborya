package com.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by oguzhanonder - 19.10.2018
 */
@Component
public class SessionTimerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTimerInterceptor.class);

    private static final long MAX_INACTIVE_SESSION_TIME = 5 * 10000;

    @Autowired
    private HttpSession session;

    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        LOGGER.info("Pre handle method - check handling start time");
        long startTime = System.currentTimeMillis();
        request.setAttribute("executionTime", startTime);
        if (UserInterceptor.isUserLogged()) {
            session = request.getSession();
            LOGGER.info("Time since last request in this session: {} ms", System.currentTimeMillis() - request.getSession().getLastAccessedTime());
            if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
                LOGGER.warn("Logging out, due to inactive session");
               // SecurityContextHolder.clearContext();
                request.logout();
                response.sendRedirect("/logout");
            }
        }
        return true;
    }

    /**
     * Executed before after handler is executed
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
        LOGGER.info("Post handle method - check execution time of handling");
        long startTime = (Long) request.getAttribute("executionTime");
        LOGGER.info("Execution time for handling the request was: {} ms", System.currentTimeMillis() - startTime);

    }
}