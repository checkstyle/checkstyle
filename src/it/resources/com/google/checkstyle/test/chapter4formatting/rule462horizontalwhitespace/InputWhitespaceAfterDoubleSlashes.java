/////////////////////////////////////////////////////////////////////////////////////////
//
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
//
/////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputWhitespaceAfterDoubleSlashes {
  String googleCheck = "Google"; //google
  // violation above ''//' must be followed by a whitespace.'

  /** Somejavadoc. */
  public void foo1() {
    int pro1 = 0; //the main variable
    // violation above ''//' must be followed by a whitespace.'

    // violation below '';' is not followed by whitespace.'
    int pro2 = 1;// the secondary variable

    int[] pro3 = {1, 2}; //[] should be preceded by datatype
    // violation above ''//' must be followed by a whitespace.'

    String pro4 = "Multiple whitespaces"; //         multiple whitespaces

    /*ignore*/
    String pro5 = "//"; // no problem

    /*byte order mark ok*/

    int pro6 = 0; /////////////////////////////// odd comment with a lot of slashes

    /// // odd comment noone uses

    int pro7 = 0; //
  }
}
