/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck">
      <property name="format" value="^[A-Z][A-Z0-9]*$"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="LocalFinalVariableName"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='Example9']]/OBJBLOCK
            /METHOD_DEF[./IDENT[@text='testMethod']]/SLIST
            /VARIABLE_DEF/IDENT[@text='testVariable1']"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example9 {
  public void testMethod() {
    final int testVariable1 = 10; // OK
    final int testVariable2 = 10; // violation, against the LocalFinalVariableName check,
                                  // name 'testVariable2' must match pattern '^[A-Z][A-Z0-9]*$'
  }
}
// xdoc section -- end
