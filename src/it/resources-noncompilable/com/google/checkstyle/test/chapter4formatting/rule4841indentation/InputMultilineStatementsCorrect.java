//non-compiled with javac: Compilable with Java17

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some class. */
public class InputMultilineStatementsCorrect {

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

  private static String testCodeBlock() {
    final String simplePropertyUpdateScript = "";
    return """
        def newInstance = params.instance;
        def existingInstance = ctx._source;
        """
        + simplePropertyUpdateScript;
  }

  void testConditionals(boolean result) {
    int collect = result
        ? 0 :
        1;
  }

  int testIfConditionMultiline(int newState, int tableName) { //indent:2 exp:2
    int flag = 0;
    if (
        (newState == 10)
            && tableName == 1 &&  flag > 0
                || (newState != 0
                    && flag < 0 && tableName == 0)) {
      flag = 1;
    }
    return flag;
  }
}
