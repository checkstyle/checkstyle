/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="nearbyTextPattern"
      value="-@cs\[(\w+)\] (\w+)"/>
    <property name="checkPattern" value="$1"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example7 {
  int a = 42; // -@cs[MagicNumber] We do not consider this number as magic.
  int b = 43; // violation "'43' is a magic number."
}
// xdoc section -- end
