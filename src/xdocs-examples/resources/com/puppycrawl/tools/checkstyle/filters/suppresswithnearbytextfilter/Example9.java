/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="checkPattern" value="LineLength"/>
    <property name="nearbyTextPattern"
      value="&lt;a href=&quot;[^&quot;]+&quot;&gt;"/>
  </module>
  <module name="LineLength">
    <property name="max" value="70"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example9 {

  int a = 42; // SUPPRESS CHECKSTYLE because I want to
  int b = 43;

  // ok, because violation message is matching suppress pattern
  String a_really_long_variable_name = "A sentence greater than 70 chars";
  // violation above 'Line is longer ...'
  String longText =
          "This is a very very very very very very very very very very long string";
  // violation above 'Line is longer ...'
  /**
   * Flag description.
   * Disabled until <a href="www.github.com/owner/repo/issue/9#comment">
   * // filtered violation above 'Line is longer ...'
   */
  public static final boolean SOME_FLAG = false;
}
// xdoc section -- end
