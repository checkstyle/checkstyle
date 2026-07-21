/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="files" value="Example(1|3)\.java"/>
      <property name="checks" value="MethodName"/>
      <property name="query" value="(//CLASS_DEF[./IDENT[@text='Example1']]
                /OBJBLOCK/METHOD_DEF/IDENT[@text='MyMethod'])|
                (//CLASS_DEF[./IDENT[@text='Example3']]/OBJBLOCK
                /METHOD_DEF/IDENT[@text='MyMethod'])"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class Example1 {
  // filtered violation below 'Name 'MyMethod' must match pattern'
  public void MyMethod() {}
  // violation below 'Name 'MyMethod2' must match pattern'
  public void MyMethod2() {}
  // violation below 'Name 'MyMethodA' must match pattern'
  public void MyMethodA() {}
  private int field = 177;
}
// xdoc section -- end
