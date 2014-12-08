package com.company.web.annotations;

import com.company.web.common.AuthableRoles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that the method with this annotation needs a authenticated
 * user. Authorization, i.e., role management, can be done by setting the role property in this
 * annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authable {
  boolean value() default true;

  /**
   * Valid values
   *
   * @return Role required for performing the annotation action
   */
  AuthableRoles needsRole();

  String uiMessage() default "You do not have necessary permissions, yet. Sorry!";
}
