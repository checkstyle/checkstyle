/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="commentFormat" value="CHECKSTYLE IGNORE THIS LINE"/>
      <property name="checkFormat" value=".*"/>
      <property name="influenceFormat" value="0"/>
    </module>
    <module name="ConstantName"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
public class Example2 {
  public static final int lowerCaseConstant = 1; // CHECKSTYLE IGNORE THIS LINE
}
// xdoc section -- end
