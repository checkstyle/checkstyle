package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Vector;

/** Test input for MethodDefPadCheck. */
public class InputFormattedMethodParamPad2 {
  /** some javadoc. */
  public InputFormattedMethodParamPad2() {
    super();
  }

  /** some javadoc. */
  public InputFormattedMethodParamPad2(int param) {
    super();
  }

  /** some javadoc. */
  public void method() {}

  /** some javadoc. */
  public void method(int param) {}

  /** some javadoc. */
  public void method(double param) {
    // invoke constructor
    InputFormattedMethodParamPad2 pad = new InputFormattedMethodParamPad2();
    pad = new InputFormattedMethodParamPad2();
    pad = new InputFormattedMethodParamPad2();

    // call method
    method();
    method();
  }

  /** some javadoc. */
  public void dottedCalls() {
    this.method();
    this.method();
    this.method();

    InputFormattedMethodParamPad2 p = new InputFormattedMethodParamPad2();
    p.method();
    p.method();
    p.method();

    Integer.parseInt("0");
    Integer.parseInt("0");
    Integer.parseInt("0");
  }

  /** some javadoc. */
  public void newArray() {
    int[] a = new int[] {0, 1};
    Vector<String> v = new Vector<String>();
    Vector<String> v1 = new Vector<String>();
  }
}
