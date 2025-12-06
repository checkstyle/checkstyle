package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputNoWhitespaceBeforeCaseDefaultColon {
  {
    switch (1) {
      case 1 : // violation '':' is preceded with whitespace.'
        break;
      case 2:
        break;
      default : // violation '':' is preceded with whitespace.'
        break;
    }
  }
}
