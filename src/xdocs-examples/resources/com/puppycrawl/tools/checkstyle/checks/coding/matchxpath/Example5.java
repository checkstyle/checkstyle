/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//CLASS_DEF[count(./OBJBLOCK/CTOR_DEF) > 1]"/>
      <message key="matchxpath.match"
           value="Classes with more than 1 constructor are not allowed"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

// xdoc section -- start
public class Example5 {
  public Example5() { }
  class Inner { // violation
    public Inner(int a) { }
    public Inner() { }
  }
}
// xdoc section -- end
