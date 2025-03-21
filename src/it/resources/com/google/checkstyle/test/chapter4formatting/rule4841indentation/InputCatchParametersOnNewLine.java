package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some javadoc.*/
public class InputCatchParametersOnNewLine {

  /** some testmethod.*/
  public void test1() {
    try {
      System.out.println("try0");
    } catch (NullPointerException
| IllegalArgumentException t) { // violation '.* incorrect indentation level 0, expected .* 8.'
    }
  }

  void test2() {
    try {
      System.out.println("try");
    } catch (
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
    Exception e) { // violation '.* incorrect indentation level 4, expected .* 8.'
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }

    try {
      System.out.println("try1");
    } catch (
    @SuppressWarnings("sometest") // violation '.* incorrect indentation level 4, expected .* 8.'
        Exception e) {
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }
  }

  void test3() {
    try {
      System.out.println("try");
    } catch (
            @SuppressWarnings("") // violation '.* incorrect indentation level 12, expected .* 8.'
        Exception e) {
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }
  }

  void test4() {
    try {
      System.out.println("try");
    } catch (NullPointerException
        | IllegalArgumentException expected) {
    }
    try {
      System.out.println("try");
    } catch (
        NullPointerException
        | IllegalArgumentException ignore) {
      // violation above '.* incorrect indentation level 8, expected .* 12.'
    }
  }
}
