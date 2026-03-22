// Java22

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

/** Tests unnamed pattern variables. */
public class InputPatternVariableNameUnnamed {

  void test(Object o1) {
    if (o1 instanceof String _) {
      System.out.println("ok");
    }

    // violation below 'Pattern variable name '_s' must match pattern'
    if (o1 instanceof String _s) {
      System.out.println(_s);
    }

    // violation below 'Pattern variable name 's_t' must match pattern'
    if (o1 instanceof String s_t) {
      System.out.println(s_t);
    }

    // violation below 'Pattern variable name 'st_' must match pattern'
    if (o1 instanceof String st_) {
      System.out.println(st_);
    }
  }
}
