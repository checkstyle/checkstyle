// non-compiled with javac: Compilable with Java15

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

  public void test2() {
    try {
      System.out.println("try");
    } catch (
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        Exception e) {
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString());
    }
  }

  private static String test3() {
    final String simplePropertyUpdateScript = "";
    return """ //indent:4 exp:4
        def newInstance = params.instance; //indent:8 exp:8
        def existingInstance = ctx._source; //indent:8 exp:8
        """
        + simplePropertyUpdateScript;
  }
}
