package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputFormattedNoWhitespaceBeforeCaseDefaultColon {
  {
    switch (1) {
      case 1:
        break;
      case 2:
        break;
      default:
        break;
    }
  }
}
