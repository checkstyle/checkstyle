package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.util.Vector;

/** Test input for MethodDefPadCheck. */
public class InputFormattedMethodParamPad2 {
  /** Some javadoc. */
  public InputFormattedMethodParamPad2() {
    super();
  }

  /** Some javadoc. */
  public InputFormattedMethodParamPad2(int param) {
    super();
  }

  /** Some javadoc. */
  public void method() {}

  /** Some javadoc. */
  public void method(int param) {}

  /** Some javadoc. */
  public void method(double param) {
    // invoke constructor
    InputFormattedMethodParamPad2 pad = new InputFormattedMethodParamPad2();
    pad = new InputFormattedMethodParamPad2();
    pad = new InputFormattedMethodParamPad2();

    // call method
    method();
    method();
  }

  /** Some javadoc. */
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

  /** Some javadoc. */
  public void newArray() {
    int[] a = new int[] {0, 1};
    Vector<String> v = new Vector<String>();
    Vector<String> v1 = new Vector<String>();
  }
}
