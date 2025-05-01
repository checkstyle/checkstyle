// non-compiled with javac: Compilable with Java15

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some javadoc. */
public class InputCatchParametersOnNewLine {

  /** some testmethod. */
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

    try {
      System.out.println("try1");
    } catch (
        @SuppressWarnings("sometest")
        Exception e) {
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }
  }

  void test3() {
    try {
      System.out.println("try");
    } catch (
        @SuppressWarnings("")
        Exception e) {
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }
  }

  void test4() {
    try {
      System.out.println("try");
    } catch (NullPointerException | IllegalArgumentException expected) {
    }
  }

  private static String test5() {
    final String simplePropertyUpdateScript = "";
    return """
        def newInstance = params.instance;
        def existingInstance = ctx._source;
      """ // violation '.* incorrect indentation level 6, expected .* 8.'
        + simplePropertyUpdateScript;
  }
}
