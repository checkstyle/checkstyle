package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

/** Some javadoc. */
public class InputSeparatorWrap {
  /** Some javadoc. */
  public void goodCase() {
    int i = 0;
    String s = "ffffooooString";
    s
        .isEmpty(); // ok
    s.isEmpty();

    foo(i,
            s); // ok
  }

  /** Some javadoc. */
  public static void foo(int i, String s) {}
}

// violation below 'Top-level class BadCase has to reside in its own source file.'
class BadCase {

  public void goodCase(int... foo) {
    int i = 0;

    String s = "ffffooooString";
    // violation below ''.' should be on a new line.'
    boolean b = s.
            isEmpty();
    foo(i
            , s); // violation '',' should be on the previous line.'
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
