/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayTypeStyle">
      <property name="javaStyle" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.arraytypestyle;

// xdoc section -- start
public class Example2 {
  int[] nums; // violation, 'Array brackets at illegal position'
  String strings[]; // OK as follows C style since 'javaStyle' set to false

  char[] toCharArray() { // OK
    return null;
  }

  byte getData()[] { // violation, 'Array brackets at illegal position'
    return null;
  }
}
// xdoc section -- end
