package com.google.checkstyle.test.chapter4formatting.rule4841indentation; //indent:0 exp:0

import java.util.Map; //indent:0 exp:0
import java.util.ArrayList;//indent:0 exp:0

public abstract class InputIndentationCorrect { //indent:0 exp:0

  static int i; //indent:2 exp:2

  int[] c = {1, 2, 3, //indent:2 exp:2
      4, 5, 6}; //indent:6 exp:6

  int b; //indent:2 exp:2

  static { //indent:2 exp:2
    i = 0; //indent:4 exp:4
  } //indent:2 exp:2

  { //indent:2 exp:2
    b = 2; //indent:4 exp:4
  } //indent:2 exp:2

  private static abstract class RangesMatcher { //indent:2 exp:2

    private static final String ZEROES = "0\u0660\u06f0" //indent:4 exp:4
        + "\u0c66\u0ce6" //indent:8 exp:8
        + "\u1c40\u1c50"; //indent:8 exp:8

    public static final InputIndentationCorrect JAVA_LETTER_OR_DIGIT = //indent:4 exp:4
        new InputIndentationCorrect() { //indent:8 exp:8
          @Override public boolean matches(char c) { //indent:10 exp:10
            return Character.isLetterOrDigit(c); //indent:12 exp:12
          } //indent:10 exp:10
        }; //indent:8 exp:8

    /** Matches no characters. */ //indent:4 exp:4
    public static final FastMatcher NONE = //indent:4 exp:4
            new FastMatcher() { //indent:12 exp:>=8
      @Override public boolean matches(char c) { //indent:6 exp:6
        return false; //indent:8 exp:8
      } //indent:6 exp:6

      @Override public String replaceFrom(CharSequence sequence, CharSequence replacement) { //indent:6 exp:6
        checkNotNull(replacement); //indent:8 exp:8
        return sequence.toString(); //indent:8 exp:8
      } //indent:6 exp:6

      private void checkNotNull(CharSequence replacement) {} //indent:6 exp:6

      @Override public String collapseFrom(CharSequence sequence, //indent:6 exp:6
          char replacement) { //indent:10 exp:10
        return sequence.toString(); //indent:8 exp:8
      } //indent:6 exp:6

      @Override //indent:6 exp:6
      public String trimTrailingFrom(CharSequence sequence) { //indent:6 exp:6
        return sequence.toString(); //indent:8 exp:8
      } //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2

  public boolean matches(char c) { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  public void foo() { //indent:2 exp:2
    int i = 0; //indent:4 exp:4
    for (; i < 9; i++) { //indent:4 exp:4
      if (veryLongLongLongCondition() //indent:6 exp:6
              || veryLongLongLongCondition2()) { //indent:14 exp:>=10
        someFooMethod("longString", "veryLongString", //indent:8 exp:8
            "superVeryExtraLongString"); //indent:12 exp:12
        if (veryLongLongLongCondition()) { //indent:8 exp:8
          while (veryLongLongLongCondition2() //indent:10 exp:10
                && veryLongLongLongCondition2()) { //indent:16 exp:>=14
            try { //indent:12 exp:12
              doSmth(); //indent:14 exp:14
            } catch (Exception e) { //indent:12 exp:12
              throw new AssertionError(e); //indent:14 exp:14
            } //indent:12 exp:12
          } //indent:10 exp:10
        } //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  public boolean veryLongLongLongCondition() { //indent:2 exp:2
    if (veryLongLongLongCondition2()) { //indent:4 exp:4
      return true; //indent:6 exp:6
    } //indent:4 exp:4
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  public boolean veryLongLongLongCondition2() { //indent:2 exp:2
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  public void someFooMethod(String longString, //indent:2 exp:2
      String superLongString, String exraSuperLongString) {} //indent:6 exp:6

  public void fooThrowMethod() //indent:2 exp:2
          throws Exception { //indent:10 exp:>=6
      /* Some code*/ //indent:6 exp:6
  } //indent:2 exp:2

  public void doSmth() { //indent:2 exp:2
    for (int h //indent:4 exp:4
          : c) { //indent:10 exp:>=8
      someFooMethod("longString", "veryLongString", //indent:6 exp:6
          "superVeryExtraLongString"); //indent:10 exp:10
    } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
