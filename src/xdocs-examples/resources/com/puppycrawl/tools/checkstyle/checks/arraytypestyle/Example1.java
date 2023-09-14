/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ArrayTypeStyle"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.arraytypestyle;

// xdoc section -- start
public class Example1 {
  int[] nums; // ok since default format checks for Java style
  String strings[]; // violation, 'Array brackets at illegal position'

  char[] toCharArray() {
    return null;
  }

  byte getData()[] { // violation, 'Array brackets at illegal position'
    return null;
  }
}
// xdoc section -- end
