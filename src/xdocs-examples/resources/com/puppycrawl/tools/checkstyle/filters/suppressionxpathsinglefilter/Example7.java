/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck"/>
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
// filtered violation below 'Name 'Example7' must match pattern '\^Abstract\.\+\$'.'
abstract class Example7 { // OK
  public void MyMethod() {} // filtered violation 'Name 'MyMethod'
                            // must match pattern '\^\[a-z\]\[a-zA-Z0-9\]\*\$'.'
}

// violation below, 'Name 'AnotherClass' must match pattern '\^Abstract\.\+\$'.'
abstract class AnotherClass {
  public void MyMethod() {} // violation, 'Name 'MyMethod'
                            // must match pattern '\^\[a-z\]\[a-zA-Z0-9\]\*\$'.'
}
// xdoc section -- end
