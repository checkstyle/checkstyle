package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some javadoc. */
public class InputClassWithChainedMethods {

  /** Some javadoc. */
  public InputClassWithChainedMethods(Object... params) {}

  /** Some javadoc. */
  public static void main(String[] args) {
    new InputClassWithChainedMethods()
        .getInstance("string_one")
    .doNothing("string_one".trim(), "string_two");
    // violation above ''method call' child has incorrect indentation level 4, expected .* 8.'

    String test =
    new InputClassWithChainedMethods("param1", "param2").getInstance().doNothing("nothing");
    // violation above ''new' has incorrect indentation level 4, expected .* 8.'

    String test2 =
    new InputClassWithChainedMethods("param1", "param2").getInstance().doNothing("nothing");
    // violation above ''new' has incorrect indentation level 4, expected .* 8.'
  }

  /** Some javadoc. */
  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  /** Some javadoc. */
  public InputClassWithChainedMethods getInstance(String... uselessParams) {
    return this;
  }
}
