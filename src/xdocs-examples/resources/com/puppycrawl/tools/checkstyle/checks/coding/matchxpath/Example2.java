/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
       value="//CTOR_DEF[count(./PARAMETERS/*) > 0]"/>
      <message key="matchxpath.match"
      value="Parameterized constructors are not allowed,
      there should be only default ctor"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class Example2 {
  public Example2(Object c) { } // violation
  public Example2(int a, HashMap<String, Integer> b) { } // violation
  public Example2() { }
}
// xdoc section -- end
