/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AbstractClassName"/>
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value=".*"/>
      <property name="query"
                value="(//CLASS_DEF[./IDENT[@text='Example7']])|
                (//CLASS_DEF[./IDENT[@text='Example7']]/OBJBLOCK/METHOD_DEF
                /IDENT[@text='MyMethod'])"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
// filtered violation below 'Name 'Example7' must match pattern'
abstract class Example7 {
  public void MyMethod() {}
  // filtered violation above 'Name 'MyMethod' must match pattern'
}

// violation below, 'Name 'AnotherClass' must match pattern'
abstract class AnotherClass {
  public void MyMethod() {}
  // violation above, 'Name 'MyMethod' must match pattern'
}
// xdoc section -- end
