package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputFormattedNoWhitespaceBeforeColonOfLabel {

  {
    label1:
    for (int i = 0; i < 10; i++) {}
  }

  /** some javadoc. */
  public void foo() {
    label2:
    while (true) {}
  }
}
