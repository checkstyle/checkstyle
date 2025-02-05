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

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example2 {
  // filtered violation below 'Name 'MyMethod1' must match pattern'
  public void MyMethod1() {}
  // filtered violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // violation below 'Name 'MyMethodA' must match pattern'
  public void MyMethodA() {}
}
// xdoc section -- end
