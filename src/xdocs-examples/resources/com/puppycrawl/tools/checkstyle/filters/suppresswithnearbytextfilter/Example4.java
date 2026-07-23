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
  // filtered violation below ''42' is a magic number'
  int a = 42; // SUPPRESS CHECKSTYLE because I want to
  int b = 43; // violation "'43' is a magic number."

  String a_really_long_variable_name = "A sentence greater than 70 chars";
  // violation above 'Line is longer ...'
  String longText =
          "This is a very very very very very very very very very very long string";
  // violation above 'Line is longer ...'
  /**
   * Flag description.
   * Disabled until <a href="www.github.com/owner/repo/issue/9#comment">
   * // violation above 'Line is longer ...'
   */
  public static final boolean SOME_FLAG = false;
}
// xdoc section -- end
