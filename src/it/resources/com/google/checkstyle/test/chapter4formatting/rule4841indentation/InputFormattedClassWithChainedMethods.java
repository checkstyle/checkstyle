package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some javadoc. */
public class InputFormattedClassWithChainedMethods {

  /** Some javadoc. */
  public InputFormattedClassWithChainedMethods(Object... params) {}

  /** Some javadoc. */
  public static void main(String[] args) {
    new InputFormattedClassWithChainedMethods()
        .getInstance("string_one")
        .doNothing("string_one".trim(), "string_two");

    String test =
        new InputFormattedClassWithChainedMethods("param1", "param2")
            .getInstance()
            .doNothing("nothing");

    String test2 =
        new InputFormattedClassWithChainedMethods("param1", "param2")
            .getInstance()
            .doNothing("nothing");
  }

  /** Some javadoc. */
  public String doNothing(String something, String... uselessParams) {
    return something;
  }

  /** Some javadoc. */
  public InputFormattedClassWithChainedMethods getInstance(String... uselessParams) {
    return this;
  }
}
