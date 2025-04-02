/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="id" value="javadocMethod"/>
    </module>
    <module name="EqualsAvoidNull">
      <property name="id" value="equalsAvoidNull"/>
    </module>
  </module>
  <module name="SuppressionSingleFilter">
    <property name="files" value="Example2.java"/>
    <property name="id" value="javadocMethod"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.filters.suppressionsinglefilter;
// xdoc section -- start
public class Example2 {

  public void checkStringEquality(String s) {
    // filtered violation below 'String literal expressions should be on the left'
    s.equals("M");
  }
  /**
   * @param p1 The first number
   */
  // filtered violation below '@return tag should be present'
  private int m2(int p1) { return p1; }
}
// xdoc section -- end
