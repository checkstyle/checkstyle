/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SeparatorWrap">
      <property name="tokens" value="METHOD_REF"/>
      <property name="option" value="nl"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;
// xdoc section -- start
import java.util.Arrays;

class Example2 {
  String[] stringArray = {"foo", "bar"};

  void fun() {
    // violation below ''::' should be on a new line'
    Arrays.sort(stringArray, String::
      compareToIgnoreCase);
    Arrays.sort(stringArray, String
      ::compareTo); // OK, because it is on a new line
  }
}
// xdoc section -- end
