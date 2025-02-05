/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck"/>
    <module name="MethodName"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value=".*"/>
      <property name="query"
                value="(//CLASS_DEF[./IDENT[@text='FileOne']])|
                (//CLASS_DEF[./IDENT[@text='FileOne']]/OBJBLOCK/METHOD_DEF
                /IDENT[@text='MyMethod'])"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
abstract class FileOne { // OK
  public void MyMethod() {} // OK
}

abstract class FileTwo { // violation, against the AbstractClassName check,
                         // it should match the pattern "^Abstract.+$"
  public void MyMethod() {} // violation, name 'MyMethod'
                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
}
// xdoc section -- end
public class Example7 {}
