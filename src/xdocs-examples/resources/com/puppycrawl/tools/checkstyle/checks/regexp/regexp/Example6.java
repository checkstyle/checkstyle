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

// (c) MyCompany

// (c) MyCompany

// xdoc section -- start
public class Example6 { // This code is copyrighted.

  private void foo() {
    System.out.println(""); // violation, 'Line matches the illegal pattern'
    // System.out.println("debug");
    // fix me. // violation above, 'Line matches the illegal pattern'
    // fix me.
  }
}
// xdoc section -- end
