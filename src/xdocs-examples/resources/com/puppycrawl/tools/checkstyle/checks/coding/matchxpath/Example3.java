/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query" value="//METHOD_DEF[./IDENT[@text='test'
              or @text='foo']]"/>
      <message key="matchxpath.match"
              value="Illegal code structure detected."/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

// xdoc section -- start
public class Example3 {
  public void test() {} // violation, 'Illegal code structure'
  public void getName() {}
  public void foo() {} // violation, 'Illegal code structure'
  public void sayHello() {}
}
// xdoc section -- end
