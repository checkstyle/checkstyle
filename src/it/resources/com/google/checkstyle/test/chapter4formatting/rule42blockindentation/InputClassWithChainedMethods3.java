package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/** Some javadoc. */
public class InputClassWithChainedMethods3 {

  /** Some javadoc. */
  public InputClassWithChainedMethods3(Object... params) {}

  /** Some javadoc. */
  public static void main(String[] args) {
    new InputClassWithChainedMethods3()
        .getInstance("string_one")
    .doNothing("string_one".trim(), "string_two");
    // violation above ''method call' child has incorrect indentation level 4, expected .* 8.'

    int length =
    new InputClassWithChainedMethods3("param1", "param2").getInstance()
        .doNothing("something").length();
    // violation 2 lines above ''new' has incorrect indentation level 4, expected .* 8.'

    int length2 =
    new InputClassWithChainedMethods3("param1", "param2").getInstance()
        .doNothing("something").length();
    // violation 2 lines above ''new' has incorrect indentation level 4, expected .* 8.'
  }

  /** Some javadoc. */
  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  /** Some javadoc. */
  public InputClassWithChainedMethods3 getInstance(String... uselessParams) {
    return this;
  }
}
