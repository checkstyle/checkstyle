/////////////////////////////////////////////////////////////////////////////////////////
//
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
//
/////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputWhitespaceAfterDoubleSlashes {
  // violation below "Trailing single-line comments must have a space after '//'."
  String googleCheck = "Google"; //google

  /** somejavadoc. */
  public void foo1() {
    // violation below "Trailing single-line comments must have a space after //."
    int pro1 = 0; //the main variable

    // violation below "';' is not followed by whitespace."
    int pro2 = 1;// the secondary variable

    // violation below "Trailing single-line comments must have a space after //."
    int[] pro3 = {1, 2}; //[] should be preceded by datatype

    String pro4 = "Multiple whitespaces"; //         multiple whitespaces

    /*ignore*/
    String pro5 = "//"; // no problem

    /*byte order mark ok*/

    int pro6 = 0; /////////////////////////////// odd comment with a lot of slashes

    /// // odd comment noone uses

    int pro7 = 0; //
  }
}
