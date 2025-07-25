package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/** some javadoc. */
public class InputFormattedClassWithChainedMethods3 {
  /** some javadoc. */
  public InputFormattedClassWithChainedMethods3() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  /** some javadoc. */
  public static void main(String[] args) {
    InputFormattedClassWithChainedMethods3 classWithChainedMethodsCorrect =
        new InputFormattedClassWithChainedMethods3();
  }

  /** some javadoc. */
  public String doNothing(String something) {
    return something;
  }
}
