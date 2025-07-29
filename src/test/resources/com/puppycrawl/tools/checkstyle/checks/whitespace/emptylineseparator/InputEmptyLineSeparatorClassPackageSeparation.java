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
// test
public class InputEmptyLineSeparatorClassPackageSeparation {
  // violation 2 lines above "'//' should be separated from previous line"
  // violation 2 lines above "'CLASS_DEF' should be separated from previous line"
  /**
  * Lines <b>method</b>.
  *
  * @return string.
  */
  int test0(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    return 0;
  }
}
