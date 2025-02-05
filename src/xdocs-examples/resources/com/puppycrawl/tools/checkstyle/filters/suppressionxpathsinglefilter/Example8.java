/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MethodName"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='Example8']]/OBJBLOCK/
                METHOD_DEF/IDENT[@text='MyMethod1' or @text='MyMethod2']"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
class Example8 {
  // filtered violation below 'Name 'MyMethod1' must match pattern'
  public void MyMethod1() {}
  // filtered violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // violation below, 'Name 'MyMethod3' must match pattern'
  public void MyMethod3() {}
}
// xdoc section -- end
