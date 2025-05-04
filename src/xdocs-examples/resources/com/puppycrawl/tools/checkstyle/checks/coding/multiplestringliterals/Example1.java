/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultipleStringLiterals"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

// xdoc section -- start
public class Example1 {
  String a = "StringContents"; // violation, "StringContents" occurs twice
  String a1 = "unchecked";
  @SuppressWarnings("unchecked") // ok, duplicate strings are ignored in annotations
  public void myTest() {
    String a2 = "StringContents";
    String a3 = "DuoString" + "DuoString"; // violation, "DuoString" occurs twice
    String a4 = "SingleString";
    String a5 = ", " + ", " + ", "; // violation, ", " occurs three times
  }
}
// xdoc section -- end
