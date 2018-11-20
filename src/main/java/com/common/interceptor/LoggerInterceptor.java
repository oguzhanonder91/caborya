package com.common.interceptor;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by oguzhanonder - 19.10.2018
 */
@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle( HttpServletRequest request,  HttpServletResponse response,  Object handler) throws Exception {
        LOGGER.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI() + getParameters(request));
        return true;
    }

    /**
     * Executed before after handler is executed
     **/
    @Override
    public void postHandle( HttpServletRequest request,  HttpServletResponse response,  Object handler,  ModelAndView modelAndView) throws Exception {
        LOGGER.info("[postHandle][" + request + "]");
    }

    /**
     * Executed after complete request is finished
     **/
    @Override
    public void afterCompletion( HttpServletRequest request,  HttpServletResponse response,  Object handler,  Exception ex) throws Exception {
        if (ex != null)
            ex.printStackTrace();
        LOGGER.info("[afterCompletion][" + request + "][exception: " + ex + "]");
    }

    private String getParameters( HttpServletRequest request) {
         StringBuffer posted = new StringBuffer();
         Enumeration<?> e = request.getParameterNames();
        if (e != null)
            posted.append("?");
        while (e != null && e.hasMoreElements()) {
            if (posted.length() > 1)
                posted.append("&");
             String curr = (String) e.nextElement();
            posted.append(curr).append("=");
            if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }

         String ip = request.getHeader("X-FORWARDED-FOR");
         String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (!Strings.isNullOrEmpty(ipAddr))
            posted.append("&_psip=" + ipAddr);
        return posted.toString();
    }

    private String getRemoteAddr( HttpServletRequest request) {
         String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            LOGGER.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }


}
