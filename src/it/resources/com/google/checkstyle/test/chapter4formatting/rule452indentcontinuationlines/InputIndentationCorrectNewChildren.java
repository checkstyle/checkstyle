package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** Some javadoc. */
public class InputIndentationCorrectNewChildren {

  private final StringBuffer filter =
      new StringBuffer(
          new CharSequence() {
            @Override
            public char charAt(int index) {
              return 'A';
            }

            public int length() {
              return 1;
            }

            public CharSequence subSequence(int start, int end) {
              return this;
            }

            public String toString() {
              return "Foo";
            }
          });
}
