package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public class InputFormattedSeparatorWrap {
  /** Some javadoc. */
  public void goodCase() {
    int i = 0;
    String s = "ffffooooString";
    s.isEmpty(); // ok
    s.isEmpty();

    foo(i, s); // ok
  }

  public static void foo(int i, String s) {}
}

// violation below 'Top-level class ExtraBadCase has to reside in its own source file.'
class ExtraBadCase {

  public void goodCase(int... foo) {
    int i = 0;

    String s = "ffffooooString";
    boolean b = s.isEmpty();
    foo(i, s);
    int[] j;
  }

  public static String foo(int i, String s) {
    String maxLength = "123";
    int truncationLength = 1;
    CharSequence seq = null;
    Object truncationIndicator = null;
    return new StringBuilder(maxLength)
        .append(seq, 0, truncationLength)
        .append(truncationIndicator)
        .toString();
  }
}
