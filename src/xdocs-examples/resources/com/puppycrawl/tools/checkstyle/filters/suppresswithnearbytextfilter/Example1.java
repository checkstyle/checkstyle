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
  // filtered violation below ''42' is a magic number'
  int a = 42;  // SUPPRESS CHECKSTYLE because I want to
  int b = 43;  // violation, "'43' is a magic number."

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
