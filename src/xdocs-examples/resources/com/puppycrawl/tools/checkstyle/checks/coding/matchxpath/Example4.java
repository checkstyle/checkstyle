/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW
                  and not(./TYPE/IDENT[@text='var'])]"/>
      <message key="matchxpath.match"
           value="New instances should be created via var keyword"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

// xdoc section -- start
public class Example4 {
  public void foo() {
    // violation below 'New instances should be created via var keyword'
    Object a = new Object();
    var b = new Object();
  }
}
// xdoc section -- end
