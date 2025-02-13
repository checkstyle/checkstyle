/*xml
<module name="Checker">
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example9.java"/>
    <property name="checks" value="FileLength"/>
  </module>
  <module name="TreeWalker">
    <module name="FileLength">
      <property name="max" value="1"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example9 {
  //filtered violation 'File length is 19 lines (max allowed is 1)'
}
// xdoc section -- end
