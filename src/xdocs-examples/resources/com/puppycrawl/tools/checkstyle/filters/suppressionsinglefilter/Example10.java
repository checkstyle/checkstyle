/*xml
<module name="Checker">
  <module name="MemberName">
    <property name="format" value="^[A-Z][a-zA-Z0-9]*$"/>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example10.java"/>
    <property name="message" value="Name 'log' must match pattern"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example10 {

  // filtered violation 'Name 'log' must match pattern'
  private String log = "Some log message";

}
// xdoc section -- end
