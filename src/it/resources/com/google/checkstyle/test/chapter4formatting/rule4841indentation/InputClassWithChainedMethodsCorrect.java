package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some javadoc. */
public class InputClassWithChainedMethodsCorrect {
  /** some javadoc. */
  public InputClassWithChainedMethodsCorrect() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  /** some javadoc. */
  public static void main(String[] args) {
    InputClassWithChainedMethodsCorrect classWithChainedMethodsCorrect =
        new InputClassWithChainedMethodsCorrect();
  }

  /** some javadoc. */
  public String doNothing(String something) {
    return something;
  }
}
