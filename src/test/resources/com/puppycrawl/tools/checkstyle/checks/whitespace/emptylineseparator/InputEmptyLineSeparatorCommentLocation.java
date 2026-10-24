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


  /** Field docs. */
  int field1;
  // violation 2 lines above "'/\*' has more than 1 empty lines before"


  // Field comment.
  int field2;
  // violation 2 lines above "'//' has more than 1 empty lines before"


  /** Method docs. */
  void method1() {}
  // violation 2 lines above "'/\*' has more than 1 empty lines before"

  void method2() {
    int local1 = 0;


    // Local variables are not class members.
    int local2 = 0;
  }
}
