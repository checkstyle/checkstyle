package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** some javadoc. */
public class InputFormattedClassWithChainedMethods2 {
  /** some javadoc. */
  public InputFormattedClassWithChainedMethods() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  /** some javadoc. */
  public static void main(String[] args) {
    InputFormattedClassWithChainedMethods2 classWithChainedMethodsCorrect =
        new InputFormattedClassWithChainedMethods2();
  }

  /** some javadoc. */
  public String doNothing(String something) {
    return something;
  }
}
