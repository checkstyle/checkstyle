/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultipleStringLiterals">
      <property name="allowedDuplicates" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

// xdoc section -- start
public class Example2 {
  String a = "StringContents"; // ok, two occurrences are allowed
  String a1 = "unchecked";
  @SuppressWarnings("unchecked") // ok, duplicate strings are ignored in annotations
  public void myTest() {
    String a2 = "StringContents";
    String a3 = "DuoString" + "DuoString"; // ok, two occurrences are allowed
    String a4 = "SingleString";
    String a5 = ", " + ", " + ", "; // violation, three occurrences are NOT allowed
  }
}
// xdoc section -- end
