package com.google.checkstyle.test.chapter5naming.rule526parameternames;

/** Some javadoc. */
public class InputCatchParameterName {
  {
    try {
      /* foo */
    } catch (Exception e) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Exception ex) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Error | Exception err) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Exception exception) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Exception exception1) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Exception noWorries) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Throwable t) {
      /* foo */
    }
    try {
      throw new InterruptedException("interruptedException");
    } catch (InterruptedException ie) {
      /* foo */
    }
    try {
      /* foo */
    } catch (Exception ok) {
      // appropriate to take no action here
    }
    try {
      /* foo */
    } catch (Exception e1) {
      try {
        /* foo */
      } catch (Exception e2) {
        /* foo */
      }
    }
    try {
      /* foo */
    } catch (Throwable t1) {
      try {
        /* foo */
      } catch (Throwable t2) {
        /* foo */
      }
    }
    try {
      /* foo */
    } catch (Exception iException) {
      // violation above 'Catch parameter name 'iException' must match pattern'
    }
    try {
      /* foo */
    } catch (Exception ex_1) { // violation 'Catch parameter name 'ex_1' must match pattern'
    }
    try {
      /* foo */
    } catch (Exception eX) { // violation 'Catch parameter name 'eX' must match pattern'
    }
    try {
      /* foo */
    } catch (Exception eXX) {
      // 2 violations above:
      //  'Abbreviation in name 'eXX' must contain no more than '1' consecutive capital letters.'
      //  'Catch parameter name 'eXX' must match pattern'
    }
    try {
      /* foo */
    } catch (Exception x_y_z) { // violation 'Catch parameter name 'x_y_z' must match pattern'
    }
    try {
      /* foo */
    } catch (Exception Ex) { // violation 'Catch parameter name 'Ex' must match pattern'
    }
  }
}
