/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="id" value="largeConstantCollection"/>
      <property name="query"
           value="//ARRAY_INIT[count(./EXPR) > 10]"/>
      <message key="matchxpath.match"
           value="Array initialization should contain at most 10 elements"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

// xdoc section -- start
public class Example6 {
  // violation below 'Array initialization should contain at most 10 elements'
  int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

  int[] small = {1, 2, 3};
}
// xdoc section -- end
