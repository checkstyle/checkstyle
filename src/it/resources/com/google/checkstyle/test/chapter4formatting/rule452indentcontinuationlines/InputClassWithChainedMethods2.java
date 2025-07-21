package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** some javadoc. */
public class InputClassWithChainedMethods2 {

  /** some javadoc. */
  public InputClassWithChainedMethods2(Object... params) {}

  /** some javadoc. */
  public static void main(String[] args) {
    new InputClassWithChainedMethods2()
        .getInstance("string_one")
    .doNothing("string_one".trim(), "string_two");
    // violation above ''method call' child has incorrect indentation level 4, expected .* 8.'

    int length =
    new InputClassWithChainedMethods2("param1", "param2").getInstance().length();
    // violation above ''new' has incorrect indentation level 4, expected .* 8.'

    int length2 =
    new InputClassWithChainedMethods2("param1", "param2").getInstance().length();
    // violation above ''new' has incorrect indentation level 4, expected .* 8.'
  }

  /** some javadoc. */
  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  /** some javadoc. */
  public InputClassWithChainedMethods2 getInstance(String... uselessParams) {
    return this;
  }
}
