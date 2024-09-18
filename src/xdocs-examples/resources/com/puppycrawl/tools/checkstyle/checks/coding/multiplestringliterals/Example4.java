/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultipleStringLiterals">
      <property name="ignoreOccurrenceContext" value=""/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

// xdoc section -- start
public class Example4 {
  String a = "StringContents"; // violation, "StringContents" occurs twice
  String a1 = "unchecked"; // // violation, "unchecked" occurs twice
  @SuppressWarnings("unchecked")
  public void myTest() {
    String a2 = "StringContents"; // OK
    String a3 = "DoubleString" + "DoubleString"; // violation,
    // "DoubleString" occurs twice
    String a4 = "SingleString"; // OK
    String a5 = ", " + ", " + ", "; // violation, ", " occurs three times
  }
}
// xdoc section -- end
