/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter"/>
  </module>
</module>
*/
// xdoc section - start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example1 {
  public Example1() {}
  // violation below 'Name 'MyMethod' must match pattern'
  public void MyMethod() {}
  // violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // violation below 'Name 'MyMethodA' must match pattern'
  public void MyMethodA() {}
  private int field = 177;
}
// xdoc section - end
