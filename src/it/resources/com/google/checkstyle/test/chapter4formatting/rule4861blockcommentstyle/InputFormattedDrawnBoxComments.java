package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/**
 * Test input for drawn box comments (formatted version).
 */
public class InputFormattedDrawnBoxComments {

  void doSomething() {
    int x = 0;
    /*
    Prepare the inputs.
    Prepare the inputs.
    Prepare the inputs.
    */
    x = 1;

    int y = 0;
    /*
     * Prepare the inputs.
     * Prepare the inputs.
     * Prepare the inputs.
     */
    y = x + 2;
  }

  /*
   * Network Configuration.
   */
  void configureNetwork() {
    /*
     * Internal Details
     */
  }

  // Valid block comments with leading asterisks (Javadoc style)
  /**
   * This is a valid Javadoc comment.
   * It has leading asterisks on each line.
   */
  void validJavadoc() {
    /* Valid single-line block comment */
    int a = 1;

    /*
     * Valid multi-line block comment
     * with leading asterisks.
     */
    int b = 2;
  }

  // More examples - these should be formatted without drawn boxes
  /*
   * This is a drawn box with equals
   */
  void drawnBoxEquals() {}

  /*
   * This is a drawn box with hashes
   */
  void drawnBoxHashes() {}

  /*
   * This is OK - dashes are commonly used for separators
   */
  void drawnBoxDashes() {}

  /*
   * This is a drawn box with underscores
   */
  void drawnBoxUnderscores() {}

  // Edge case: Less than 30 repeating characters should be OK
  /*
   * This is OK - only 28 asterisks
   */
  void shortLine() {}

  // Edge case: Exactly 30 repeating characters - should be formatted properly
  /*
   * Content here
   */
  void exactly30() {}
}

