package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** Some javadoc. */
public class InputFormattedClassWithChainedMethods2 {
  /** Some javadoc. */
  public InputFormattedClassWithChainedMethods2() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  /** Some javadoc. */
  public static void main(String[] args) {
    InputFormattedClassWithChainedMethods2 classWithChainedMethodsCorrect =
        new InputFormattedClassWithChainedMethods2();
  }

  /** Some javadoc. */
  public String doNothing(String something) {
    return something;
  }
}
