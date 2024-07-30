package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some javadoc. */
public abstract class InputIndentationCorrect {

  static int i;

  static {
    i = 0;
  }

  int[] pqr = {
    1, 2, 3,
    4, 5, 6
  };
  int abc;

  {
    abc = 2;
  }

  public boolean matches(char c) {
    return false;
  }

  /** some javadoc. */
  public void foo() {
    int i = 0;
    for (; i < 9; i++) {
      if (veryLongLongLongCondition() || veryLongLongLongCondition2()) {
        someFooMethod("longString", "veryLongString", "superVeryExtraLongString");
        if (veryLongLongLongCondition()) {
          while (veryLongLongLongCondition2() && veryLongLongLongCondition2()) {
            try {
              doSmth();
            } catch (Exception e) {
              throw new AssertionError(e);
            }
          }
        }
      }
    }
  }

  public boolean veryLongLongLongCondition() {
    return veryLongLongLongCondition2();
  }

  public boolean veryLongLongLongCondition2() {
    return false;
  }

  public void someFooMethod(
      String longString, String superLongString, String exraSuperLongString) {}

  public void fooThrowMethod() throws Exception {
    /* Some code*/
  }

  /** some javadoc. */
  public void doSmth() {
    for (int h : pqr) {
      someFooMethod("longString", "veryLongString", "superVeryExtraLongString");
    }
  }

  private abstract static class RangesMatcher {

    public static final InputIndentationCorrect JAVA_LETTER_OR_DIGIT =
        new InputIndentationCorrect() {
          @Override
          public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
          }
        };

    /** Matches no characters. */
    public static final InputFastMatcher NONE =
        new InputFastMatcher() {
          @Override
          public boolean matches(char c) {
            return false;
          }

          @Override
          public String replaceFrom(CharSequence seq, CharSequence repl) {
            checkNotNull(repl);
            return seq.toString();
          }

          private void checkNotNull(CharSequence replacement) {}

          @Override
          public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
          }

          @Override
          public String trimTrailingFrom(CharSequence sequence) {
            return sequence.toString();
          }
        };

    private static final String ZEROES = "" + "";
  }
}
