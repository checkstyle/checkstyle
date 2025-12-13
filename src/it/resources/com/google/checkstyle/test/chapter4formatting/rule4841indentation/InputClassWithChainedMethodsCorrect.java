package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some javadoc. */
public class InputClassWithChainedMethodsCorrect {
  /** Some javadoc. */
  public InputClassWithChainedMethodsCorrect() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  /** Some javadoc. */
  public static void main(String[] args) {
    InputClassWithChainedMethodsCorrect classWithChainedMethodsCorrect =
        new InputClassWithChainedMethodsCorrect();
  }

  /** Some javadoc. */
  public String doNothing(String something) {
    return something;
  }
}
