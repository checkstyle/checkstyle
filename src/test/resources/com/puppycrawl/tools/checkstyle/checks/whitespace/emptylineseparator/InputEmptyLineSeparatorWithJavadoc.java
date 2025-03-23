/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator">
      <property name="allowNoEmptyLineBetweenFields" value="true"/>
      <property name="allowMultipleEmptyLines" value="false"/>
      <property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorWithJavadoc {
  /** Some javadoc. */
  int test0(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    return 0;
  }


    /**
     * Test.
     */
    void myMethod() {}
    // violation above "'METHOD_DEF' has more than 1 empty lines before"

  /** some lines to test the one line javadoc. */
  void violateColumnAfterTabs() {
    // with tab-width 8 all statements below start at the same column,
    // with different combinations of ' ' and '\t' before the statement
    int tab0 = 1;
  }

  /** Some javadoc. */
  int test1(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    return 0;
  }

  /** Some javadoc. */


  int test2(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    // violation above "'METHOD_DEF' has more than 1 empty lines before"
    return 0;
  }


  /** Some javadoc. */

  int test3(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    // violation above "'METHOD_DEF' has more than 1 empty lines before"
    return 0;
  }
  /** Some javadoc. */
  int test4(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    // violation above "'METHOD_DEF' should be separated from previous line"
    return 0;
  }


  // test
  /** Some javadoc. */

  int test5(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    // violation above "'METHOD_DEF' has more than 1 empty lines before"
    return 0;
  }

  // test
  /** Some javadoc. */

  int test6(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    // violation below "There is more than 1 empty line after this line"
    return 0;


  } // test

  int test7(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    return 0;
  }

}
