package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some javadoc. */
public class InputClassWithChainedMethods {

  /** some javadoc. */
  public InputClassWithChainedMethods(Object... params) {}

  /** some javadoc. */
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

  /** some javadoc. */
  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  /** some javadoc. */
  public InputClassWithChainedMethods getInstance(String... uselessParams) {
    return this;
  }
}
