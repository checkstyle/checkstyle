/////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
/////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;
/** some javadoc. */
public class InputWhitespaceAfterDoubleSlashesCorrect {
  String googleCheck = "Google"; // google

  /** somejavadoc. */
  public void foo1() {
    int pro1 = 0; // the main variable

    int pro2 = 1; // the secondary variable

    int[] pro3 = {1, 2}; // [] should be preceded by datatype

    String pro4 = "Multiple whitespaces"; //         multiple whitespaces

    String pro5 = "//"; // no problem
  }
}
