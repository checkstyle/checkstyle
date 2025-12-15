// Java21

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some javadoc. */
public class InputFormattedCatchParametersOnNewLine {

  /** Some test Method. */
  public void test1() {
    try {
      System.out.println("try0");
    } catch (NullPointerException | IllegalArgumentException expected) {
      // ignore
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
        @SuppressWarnings("suppression")
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
      // ignore
    }
  }

  private static String test5() {
    final String simplePropertyUpdateScript =
        """
        s
        """;

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    // violation below 'Text block content indentation is less than the opening quotes'
    return ("""
      def newInstance = params.instance;
      def existingInstance = ctx._source;
    """ // violation 'Text-block quotes are not vertically aligned'
        // violation above '.* incorrect indentation level 4, expected .* 8.'
        + simplePropertyUpdateScript);
  }

  void test2Incorrect(boolean result) {
    int collect = result ? 0 : 1;
  }
}
