/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
       value="//METHOD_DEF[.//LITERAL_PRIVATE and
       following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]"/>
      <message key="matchxpath.match"
       value="Private methods must appear after public methods"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

// xdoc section -- start
public class Example1 {
  public void method1() { }
  private void method2() { } // violation
  public void method3() { }
  private void method4() { } // violation
  public void method5() { }
  private void method6() { }
}
// xdoc section -- end
