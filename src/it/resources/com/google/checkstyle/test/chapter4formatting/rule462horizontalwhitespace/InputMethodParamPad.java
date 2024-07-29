package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Vector;

/** Test input for MethodDefPadCheck. */
public class InputMethodParamPad {
  public InputMethodParamPad() {
    super();
  }

  public InputMethodParamPad (int param) { // violation ''(' is preceded with whitespace.'
    super (); // violation ''(' is preceded with whitespace.'
  }

  public void method() {}

  public void method (int param) {} // violation ''(' is preceded with whitespace.'

  /** some javadoc. */
  public void method(double param) {
    // invoke constructor
    InputMethodParamPad pad = new InputMethodParamPad();
    pad = new InputMethodParamPad (); // violation ''(' is preceded with whitespace.'
    pad = new
            InputMethodParamPad();

    // call method
    method();
    method (); // violation ''(' is preceded with whitespace.'
  }

  /** some javadoc. */
  public void dottedCalls() {
    this.method();
    this.method (); // violation ''(' is preceded with whitespace.'
    this
        .method();

    InputMethodParamPad p = new InputMethodParamPad();
    p.method();
    p.method (); // violation ''(' is preceded with whitespace.'
    p
         .method();

    java.lang.Integer.parseInt("0");
    java.lang.Integer.parseInt ("0"); // violation ''(' is preceded with whitespace.'
    java.lang.Integer
       .parseInt("0");
  }

  /** some javadoc. */
  public void newArray() {
    int[] a = new int[]{0, 1};
    java.util.Vector<String> v = new java.util.Vector<String>();
    java.util.Vector<String> v1 = new Vector<String>();
  }
}
