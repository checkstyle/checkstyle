/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="idPattern" value="ignoreMe"/>
  </module>
  <module name="LineLength">
    <property name="max" value="55"/>
  </module>
  <module name="TreeWalker">
    <module name="MagicNumber">
      <property name="id" value="ignoreMe"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example4 {
  int a = 42; // SUPPRESS CHECKSTYLE because I want to
  static final int LONG_VAR_NAME_TO_TAKE_MORE_THAN_55_CHARS = 22;
  // violation above 'Line is longer ...'
}
// xdoc section -- end
