/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//CTOR_DEF[count(./PARAMETERS/*) > 0]"/>
      <message key="matchxpath.match"
           value="Parameterized constructors are not allowed"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class UseCase1 {
  // violation below 'Parameterized constructors are not allowed'
  public UseCase1(Object c) { }
  // violation below 'Parameterized constructors are not allowed'
  public UseCase1(int a, HashMap<String, Integer> b) { }
  public UseCase1() { }
}
// xdoc section -- end
