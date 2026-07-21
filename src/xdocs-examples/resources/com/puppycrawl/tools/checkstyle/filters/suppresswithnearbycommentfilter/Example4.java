/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="checkC" value="false"/>
    </module>
    <module name="NoWhitespaceAfter"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
public class Example4 {
  // filtered violation below ''int' is followed by whitespace'
  public static final int [] array = {}; // SUPPRESS CHECKSTYLE NoWhitespaceAfter

  public static final int lowerCaseConstant = 1; // CHECKSTYLE IGNORE THIS LINE

  public void testMethod() {
    try {
    }

    catch (RuntimeException ex) {

    }
  }
}
// xdoc section -- end
