/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format"
        value="// This code is copyrighted\n// \(c\) MyCompany"/>
      <property name="duplicateLimit" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// This code is copyrighted
// (c) MyCompany  // violation below 'Found duplicate pattern'
// This code is copyrighted
// (c) MyCompany

// xdoc section -- start
public class Example4 {

  private void foo() {
    System.out.println("");
    // System.out.println("debug");

    // fix me.
  }
}
// xdoc section -- end
