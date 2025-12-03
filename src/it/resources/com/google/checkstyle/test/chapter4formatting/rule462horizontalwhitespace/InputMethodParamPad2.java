package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Vector;

/** Test input for MethodDefPadCheck. */
public class InputMethodParamPad2 {
  /** Some javadoc. */
  public InputMethodParamPad2() {
    super();
  }

  /** Some javadoc. */
  public InputMethodParamPad2 (int param) { // violation ''(' is preceded with whitespace.'
    super (); // violation ''(' is preceded with whitespace.'
  }

  /** Some javadoc. */
  public void method() {}

  /** Some javadoc. */
  public void method (int param) {} // violation ''(' is preceded with whitespace.'

  /** Some javadoc. */
  public void method(double param) {
    // invoke constructor
    InputMethodParamPad2 pad = new InputMethodParamPad2();
    pad = new InputMethodParamPad2 (); // violation ''(' is preceded with whitespace.'
    pad = new
            InputMethodParamPad2();

    // call method
    method();
    method (); // violation ''(' is preceded with whitespace.'
  }

  /** Some javadoc. */
  public void dottedCalls() {
    this.method();
    this.method (); // violation ''(' is preceded with whitespace.'
    this
        .method();

    InputMethodParamPad2 p = new InputMethodParamPad2();
    p.method();
    p.method (); // violation ''(' is preceded with whitespace.'
    p
         .method();

    java.lang.Integer.parseInt("0");
    java.lang.Integer.parseInt ("0"); // violation ''(' is preceded with whitespace.'
    java.lang.Integer
       .parseInt("0");
  }

  /** Some javadoc. */
  public void newArray() {
    int[] a = new int[]{0, 1};
    java.util.Vector<String> v = new java.util.Vector<String>();
    java.util.Vector<String> v1 = new Vector<String>();
  }
}
