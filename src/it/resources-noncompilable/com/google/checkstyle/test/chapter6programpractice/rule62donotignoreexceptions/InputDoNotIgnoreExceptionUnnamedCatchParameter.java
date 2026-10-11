// non-compiled with javac: Compilable with Java25

package com.google.checkstyle.test.chapter6programpractice.rule62donotignoreexceptions;

/** Some javadoc. */
public class InputDoNotIgnoreExceptionUnnamedCatchParameter {

  void method(String response) {
    try {
      int x = 1 / 10;
    } catch (ArithmeticException _) {
    }
    // violation 2 lines above 'Empty catch block'

    try {
      int i = Integer.parseInt(response);
      handleNumericResponse(i);
    } catch (NumberFormatException _) {
      // it's not numeric; that's fine, just continue
    }
    handleTextResponse(response);
  }

  void handleNumericResponse(int num) {
    System.out.println("Number is " + num);
  }

  void handleTextResponse(String response) {
    System.out.println("Can't be converted to number " + response);
  }
}
