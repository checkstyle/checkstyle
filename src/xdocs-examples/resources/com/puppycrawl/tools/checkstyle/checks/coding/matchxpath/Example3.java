/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//METHOD_DEF[./IDENT[@text='test' or @text='foo']]"/>
      <message key="matchxpath.match"
           value="Method name should not be 'test' or 'foo'"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

// xdoc section -- start
public class Example3 {
  // violation below 'Method name should not be test or foo'
  public void test() {}
  public void getName() {}
  // violation below 'Method name should not be test or foo'
  public void foo() {}
  public void sayHello() {}
}
// xdoc section -- end
