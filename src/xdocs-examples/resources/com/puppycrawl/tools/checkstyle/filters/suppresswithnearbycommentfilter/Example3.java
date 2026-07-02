/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="commentFormat"
                value="ok,?\s*(?:allowed\s+)?to catch (\w+) here"/>
      <property name="checkFormat" value="IllegalCatchCheck"/>
      <property name="messageFormat" value="$1"/>
      <property name="influenceFormat" value="-1"/>
    </module>
    <module name="IllegalCatch"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
public class Example3 {

  public static final int [] array = {}; // SUPPRESS CHECKSTYLE NoWhitespaceAfter

  public static final int lowerCaseConstant = 1; // CHECKSTYLE IGNORE THIS LINE

  public void testMethod() {
    try {
    }
    // filtered violation below 'Catching 'RuntimeException' is not allowed'
    catch (RuntimeException ex) {
      // ok, allowed to catch RuntimeException here
    }
  }
}
// xdoc section -- end
