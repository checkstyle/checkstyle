/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="nearbyTextPattern"
      value="@cs-: (\w+) for ([+-]\d+) lines"/>
    <property name="checkPattern" value="$1"/>
    <property name="lineRange" value="$2"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example8 {
  int a = 42; // @cs-: MagicNumber for +3 lines
  int b = 43;
  int c = 44;
  int d = 45;
  int e = 46; // violation "'46' is a magic number."
}
// xdoc section -- end
