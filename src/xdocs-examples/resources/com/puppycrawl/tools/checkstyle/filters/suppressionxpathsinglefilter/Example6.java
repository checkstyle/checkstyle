/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter.example6files.MyMethod"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="Example6"/>
      <property name="checks" value="MyMethod"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example6 {
  // filtered violation below 'Name 'MyMethod' must match pattern '\^\[a-z\](_\?\[a-zA-Z0-9\]\+)\*\$''
  public void MyMethod() {}
}
// xdoc section -- end
