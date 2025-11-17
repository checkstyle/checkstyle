/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MemberName">
      <property name="id" value="customMemberName"/>
    </module>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example4.java"/>
    <property name="id" value="customMemberName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example4 {
  // filtered violation below 'Name 'MyVariable' must match pattern'
  private int MyVariable = 5;
}
// xdoc section -- end
