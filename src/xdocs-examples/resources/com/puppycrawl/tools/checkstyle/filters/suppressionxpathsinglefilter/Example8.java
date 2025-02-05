/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MethodName"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='TestClassExample']]/OBJBLOCK/
                METHOD_DEF/IDENT[@text='MyMethod1' or @text='MyMethod2']"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
class TestClassExample {
  public void MyMethod1() {} // OK
  public void MyMethod2() {} // OK
  public void MyMethod3() {} // violation, name 'MyMethod3' must
                             // match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
public class Example8 {}
