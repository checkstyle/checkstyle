/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter"/>
  <module name="TreeWalker">
    <module name="MagicNumber"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example1 {
  int hoursInDay = 24; // SUPPRESS CHECKSTYLE because it is too obvious
  int daysInWeek = 7; // violation, "'7' is a magic number."
}
// xdoc section -- end
