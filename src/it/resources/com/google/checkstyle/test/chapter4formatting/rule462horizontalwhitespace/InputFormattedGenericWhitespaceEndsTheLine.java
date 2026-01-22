package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputFormattedGenericWhitespaceEndsTheLine {
  /** Some javadoc. */
  public boolean returnsGenericObjectAtEndOfLine(Object otherObject) {
    return otherObject instanceof Enum<?>;
  }
}
