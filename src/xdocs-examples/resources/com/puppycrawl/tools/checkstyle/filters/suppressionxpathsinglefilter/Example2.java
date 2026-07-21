/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MethodName"/>
      <property name="message" value="MyMethod[0-9]"/>
    </module>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example2 {
  // violation below 'Name 'MyMethod' must match pattern'
  public void MyMethod() {}
  // filtered violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // violation below 'Name 'MyMethodA' must match pattern'
  public void MyMethodA() {}
  private int field = 177;
}
// xdoc section -- end
