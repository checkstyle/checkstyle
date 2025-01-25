/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="commentFormat"
                value="ALLOW (\w+) ON PREVIOUS LINE"/>
      <property name="influenceFormat" value="-1"/>
    </module>
    <module name="MemberName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
public class Example5 {
  private int D2;
  // ALLOW MemberName ON PREVIOUS LINE
}
// xdoc section -- end
