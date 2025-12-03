package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** Some javadoc. */
public class InputClassWithChainedMethods2 {

  /** Some javadoc. */
  public InputClassWithChainedMethods2(Object... params) {}

  /** Some javadoc. */
  public static void main(String[] args) {
    new InputClassWithChainedMethods2()
        .getInstance("string_one")
    .doNothing("string_one".trim(), "string_two");
    // violation above ''method call' child has incorrect indentation level 4, expected .* 8.'

    int length =
    new InputClassWithChainedMethods2("param1", "param2").getInstance()
        .doNothing("something").length();
    // violation 2 lines above ''new' has incorrect indentation level 4, expected .* 8.'

    int length2 =
    new InputClassWithChainedMethods2("param1", "param2").getInstance()
        .doNothing("something").length();
    // violation 2 lines above ''new' has incorrect indentation level 4, expected .* 8.'
  }

  /** Some javadoc. */
  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  /** Some javadoc. */
  public InputClassWithChainedMethods2 getInstance(String... uselessParams) {
    return this;
  }
}
