package com.google.checkstyle.test.chapter3filestructure.rule3421overloadsplit;

/** Some javadoc. */
public class InputOverloadsNeverSplitDifferentModifier {
  int first;
  int second;

  /** Some javadoc. */
  public InputOverloadsNeverSplitDifferentModifier() {
    this(1, 2);
  }

  /** Some javadoc. */
  public InputOverloadsNeverSplitDifferentModifier(int first) {
    this(first, 2);
  }

  /** Some javadoc. */
  public void method() {
    foo(1);
  }

  static void method(int arg) {
    foo(arg);
  }

  // violation 2 lines below 'Constructors should be grouped together.*'

  private InputOverloadsNeverSplitDifferentModifier(int first, int second) {
    this.first = first;
    this.second = second;
  }

  // violation 2 lines below 'All overloaded methods should be placed next to each other. .* '23'

  private void method(int arg1, int arg2) {
    foo(String.valueOf(arg1), String.valueOf(arg2));
  }

  static void foo(int arg) {}

  private void someMethod() {}

  /** Some javadoc. */
  public void foo(String arg1, String arg2) {}

  // violation 2 lines above 'All overloaded methods should be placed next to each other. .* '40'

  private void someMethod1() {}
}
