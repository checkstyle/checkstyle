/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="commentFormat" value="SUPPRESS CHECKSTYLE (\w+) id:(\S+)"/>
      <property name="checkFormat" value="$1"/>
      <property name="idFormat" value="$2"/>
    </module>
    <module name="NoWhitespaceAfter">
      <property name="id" value="whitespaceCheckId"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
public class Example6 {
  // violation below ''int' is followed by whitespace'
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
