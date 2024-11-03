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
  public void test() {} // violation
  public void getName() {}
  public void foo() {} // violation
  public void sayHello() {}
}
// xdoc section -- end
