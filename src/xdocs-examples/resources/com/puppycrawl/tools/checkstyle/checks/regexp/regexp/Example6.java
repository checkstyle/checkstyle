/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="System\.out\.println"/>
      <property name="illegalPattern" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// xdoc section -- start
public class Example6 {
  private void foo() {
    System.out.println(""); // violation, 'Line matches the illegal pattern'
    System.out.
      println("");
    // System.out.println("debug");
    // violation above, 'Line matches the illegal pattern'
  }
}
// xdoc section -- end
