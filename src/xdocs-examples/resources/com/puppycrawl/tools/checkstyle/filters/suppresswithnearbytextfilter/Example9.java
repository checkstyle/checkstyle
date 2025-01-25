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
  /**
   * Flag description.
   * Disabled until <a href="www.github.com/owner/repo/issue/9#comment">
   */
  public static final boolean SOME_FLAG = false;
}
// xdoc section -- end
