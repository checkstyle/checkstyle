package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputFormattedGenericWhitespaceEndsTheLine {
  /** some javadoc. */
  public boolean returnsGenericObjectAtEndOfLine(Object otherObject) {
    return otherObject instanceof Enum<?>;
  }
}
