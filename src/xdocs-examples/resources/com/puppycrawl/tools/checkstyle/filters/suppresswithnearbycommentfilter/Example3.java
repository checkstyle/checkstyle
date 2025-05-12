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
  public void doStuff() {
    try {
      // blah blah blah
    }
    // filtered violation below 'Catching 'RuntimeException' is not allowed'
    catch(RuntimeException re) {
      // ok, allowed to catch RuntimeException here
    }
  }
}
// xdoc section -- end
