/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowMultipleEmptyLines" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorCommentLocation {


  /** No modifier field. */
  int f1;
  // violation 2 lines above "'/\*' has more than 1 empty lines before"


  // Public field.
  public int f2;
  // violation 2 lines above "'//' has more than 1 empty lines before"


  /* Private static final. */
  private static final int F3 = 1;
  // violation 2 lines above "'/\*' has more than 1 empty lines before"


  /** No modifier method. */
  void m1() {}
  // violation 2 lines above "'/\*' has more than 1 empty lines before"


  // Private method.
  private void m2() {}
  // violation 2 lines above "'//' has more than 1 empty lines before"


  /** Constructor. */
  public InputEmptyLineSeparatorCommentLocation() {}
  // violation 2 lines above "'/\*' has more than 1 empty lines before"


  /** First of two comments. */


  // Second of two comments.
  void twoComments() {
    // violation 5 lines above "'/\*' has more than 1 empty lines before"
    // violation 3 lines above "'//' has more than 1 empty lines before"
  }

  @SuppressWarnings("deprecation")


  private String annotated() {
    // violation 4 lines above "'METHOD_DEF' has more than 1 empty lines before"
    return "";
  }

  void local() {
    int a = 0;


    // Local comment, not a member.
    int b = 0;
  }

  interface I {
    int A = 1;


    /** Interface method. */
    void im();
    // violation 2 lines above "'/\*' has more than 1 empty lines before"
  }

  enum E {
    ONE;


    /** Enum field. */
    int x;
    // violation 2 lines above "'/\*' has more than 1 empty lines before"
  }

  record R(int a) {
    static int s;


    /** Record method. */
    int m() { return a; }
    // violation 2 lines above "'/\*' has more than 1 empty lines before"
  }
}
