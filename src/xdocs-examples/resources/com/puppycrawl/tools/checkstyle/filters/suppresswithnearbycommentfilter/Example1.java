/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter"/>
    <module name="NoWhitespaceAfter"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
public class Example1 {
  private int [] array; // SUPPRESS CHECKSTYLE NoWhitespaceAfter
}
// xdoc section -- end
