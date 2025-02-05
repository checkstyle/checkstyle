/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck">
      <property name="format" value="^[A-Z][A-Z0-9]*$"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value=".*"/>
      <property name="query" value="//METHOD_DEF[./IDENT[@text='TestMethod1']]
            /descendant-or-self::node()"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

// xdoc section -- start
public class Example13 {
  public void TestMethod1() { // OK
    final int num = 10; // OK
  }

  public void TestMethod2() { // violation, against the MethodName check,
                              // name 'TestMethod2' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
    final int num = 10; // violation, against the LocalFinalVariableName check,
                        // name 'num' must match pattern '^[A-Z][A-Z0-9]*$'
  }
}
// xdoc section -- end
