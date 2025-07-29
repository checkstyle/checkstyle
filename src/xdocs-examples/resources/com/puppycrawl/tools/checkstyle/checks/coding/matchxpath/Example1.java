/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//METHOD_DEF[.//LITERAL_PRIVATE
                  and following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]"/>
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
  // violation below 'Private methods must appear after public methods'
  private void method2() { }
  public void method3() { }
  // violation below 'Private methods must appear after public methods'
  private void method4() { }
  public void method5() { }
  private void method6() { }
}
// xdoc section -- end
