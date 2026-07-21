/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MagicNumber"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="Example6"/>
      <property name="checks" value="MagicNumber"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example6 {

  public void MyMethod() {}

  public void MyMethod2() {}

  public void MyMethodA() {}
  private int field = 177; // filtered violation ''177' is a magic number.'
}
// xdoc section -- end
