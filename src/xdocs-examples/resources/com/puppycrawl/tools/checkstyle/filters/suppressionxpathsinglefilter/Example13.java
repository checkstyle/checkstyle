/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalFinalVariableName">
      <property name="format" value="^[A-Z][A-Z0-9]*$"/>
    </module>
    <module name="MethodName">
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
  // filtered violation below 'Name 'TestMethod1' must match pattern'
  public void TestMethod1() {
    // filtered violation below 'Name 'num' must match pattern'
    final int num = 10;
  }

  // violation below, 'Name 'TestMethod2' must match pattern'
  public void TestMethod2() {
    // violation below, 'Name 'num' must match pattern'
    final int num = 10;
  }
}
// xdoc section -- end
