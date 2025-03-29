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

// violation 8 lines below 'has more than 1 empty lines before'

public class InputEmptyLineSeparatorWithJavadoc {


    /**
     * Test.
     */
    void myMethod() {}

}
