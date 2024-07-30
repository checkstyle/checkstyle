package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** some javadoc. */
public class ClassWithChainedMethodsCorrect {
  /** some javadoc. */
  public ClassWithChainedMethodsCorrect() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  public static void main(String[] args) {
    ClassWithChainedMethodsCorrect classWithChainedMethodsCorrect =
        new ClassWithChainedMethodsCorrect();
  }

  public String doNothing(String something) {
    return something;
  }
}
