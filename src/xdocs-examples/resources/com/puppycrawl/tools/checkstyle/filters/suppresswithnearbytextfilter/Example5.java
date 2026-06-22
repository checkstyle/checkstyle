/*xml
<module name="Checker">
  <module name="SuppressWithNearbyTextFilter">
    <property name="lineRange" value="2"/>
  </module>
  <module name="UniqueProperties"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;
// xdoc section -- start
public class Example5 {

  int a = 42; // SUPPRESS CHECKSTYLE because I want to
  int b = 43;

  // ok, because violation message is matching suppress pattern
  String a_really_long_variable_name = "A sentence greater than 70 chars";

  String longText =
          "This is a very very very very very very very very very very long string";

  /**
   * Flag description.
   * Disabled until <a href="www.github.com/owner/repo/issue/9#comment">
   *
   */
  public static final boolean SOME_FLAG = false;
}
// xdoc section -- end
