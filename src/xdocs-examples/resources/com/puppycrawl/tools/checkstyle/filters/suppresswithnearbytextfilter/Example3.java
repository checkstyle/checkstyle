/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="messagePattern" value=".*Line.*"/>
    <property name="nearbyTextPattern" value=".*"/>
  </module>
  <module name="LineLength">
    <property name="max" value="10"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example3 {
  // ok, because violation message is matching suppress pattern
  int a_really_long_variable_name = 10;
}
// xdoc section -- end
