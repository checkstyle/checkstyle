package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some class. */
public class InputCatchParametersOnNewLineCorrect {

  /** some test method. */
  public void test1() {
    try {
      System.out.println("try0");
    } catch (NullPointerException | IllegalArgumentException expected) {
    }
  }

  void test2() {
    try {
      System.out.println("try");
    } catch (
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        Exception e) {
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }
  }
}

