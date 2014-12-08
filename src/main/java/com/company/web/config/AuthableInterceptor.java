package com.company.web.config;

import com.company.web.annotations.Authable;
import com.company.web.common.Cookies;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Intercepts all methods with the @Authable annotation Also adds a debug cookie, to print debug
 * variables on screen
 */
public class AuthableInterceptor implements HandlerInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthableInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    addDebugCookie(request, response);

    HandlerMethod handlerMethod;
    if (handler instanceof HandlerMethod) {
      handlerMethod = (HandlerMethod) handler;
    } else {
      LOGGER.warn("Object handler is not a HandlerMethod. No idea what to do. Moving on.");
      return true;
    }

    if (request == null) {
      LOGGER.warn("Request was null for method: " + handlerMethod.getMethod().toGenericString());
      return true;
    }

    Authable authable = handlerMethod.getMethodAnnotation(Authable.class);
    if (authable != null && authable.value()) { // @Authable is set to true.. i.e., validate session
      //Validation rules here
      return true;
    } else { // @Authable annotation not found..continue as usual
      // DO nothing
      return true;
    }
  }

  private void addDebugCookie(HttpServletRequest request, HttpServletResponse response) {
    String debugParam = request.getParameter("debug");
    if (Strings.isNullOrEmpty(debugParam)) {
      // Do nothing
    } else if (debugParam.equalsIgnoreCase("true")) {
      response.addCookie(Cookies.DEBUG_TRUE);
    } else {
      response.addCookie(Cookies.DEBUG_FALSE);
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {}

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {}
}
