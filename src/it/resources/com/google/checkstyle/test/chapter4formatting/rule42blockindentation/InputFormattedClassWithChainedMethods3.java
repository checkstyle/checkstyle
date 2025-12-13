package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

/** Some javadoc. */
public class InputFormattedClassWithChainedMethods3 {
  /** Some javadoc. */
  public InputFormattedClassWithChainedMethods3() {

    String someString = "";

    String chained1 = doNothing(someString.concat("zyx").concat("255, 254, 253"));

    doNothing(someString.concat("zyx").concat("255, 254, 253"));
  }

  /** Some javadoc. */
  public static void main(String[] args) {
    InputFormattedClassWithChainedMethods3 classWithChainedMethodsCorrect =
        new InputFormattedClassWithChainedMethods3();
  }

  /** Some javadoc. */
  public String doNothing(String something) {
    return something;
  }
}
