package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputNoWhitespaceBeforeColonOfLabel {

  {
    label1 : // violation '':' is preceded with whitespace.'
    for (int i = 0; i < 10; i++) {
    }
  }

  /** some javadoc. */
  public void foo() {
    label2:
    while (true) {
    }
  }
}
