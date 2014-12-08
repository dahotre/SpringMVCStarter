package com.company.web.exception;

import com.company.web.annotations.Authable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when some an unauthorized action is triggered
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthableException extends Exception {

  public static final String LOGIN_REQUIRED = "Login required";

  public AuthableException() {
    super(LOGIN_REQUIRED);
  }

  public AuthableException(final String message) {
    super(message);
  }

  public AuthableException(final Authable authable) {
    super(authable.uiMessage());
  }

  private String requestURI;

  public String getRequestURI() {
    return requestURI;
  }

  public void setRequestURI(String requestURI) {
    this.requestURI = requestURI;
  }

  public AuthableException withRequestURI(String requestURI) {
    setRequestURI(requestURI);
    return this;
  }

}
