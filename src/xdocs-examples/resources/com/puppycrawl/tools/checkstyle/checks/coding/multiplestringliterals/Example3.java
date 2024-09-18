/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MultipleStringLiterals">
      <property name="ignoreStringsRegexp"
        value='^(("")|(", "))$'/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

// xdoc section -- start
public class Example3 {
  String a = "StringContents"; // violation, "StringContents" occurs twice
  String a1 = "unchecked";
  @SuppressWarnings("unchecked") // OK, duplicate strings are ignored in annotations
  public void myTest() {
    String a2 = "StringContents"; // OK
    String a3 = "DoubleString" + "DoubleString"; // violation, "DoubleString" occurs twice
    String a4 = "SingleString"; // OK
    String a5 = ", " + ", " + ", "; // OK, multiple occurrences of ", " are allowed
  }
}
// xdoc section -- end
