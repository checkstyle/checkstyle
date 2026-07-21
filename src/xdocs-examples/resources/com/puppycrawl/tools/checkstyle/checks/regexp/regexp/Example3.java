/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="// \(c\) MyCompany"/>
      <property name="duplicateLimit" value="0"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// xdoc section -- start
// (c) MyCompany
// (c) MyCompany
// This code is copyrighted.
public class Example3 { // violation 2 lines above 'Found duplicate pattern'
  private void foo() {
    System.out.println("");
    // System.out.println("debug");
    // fix me.
    // fix me.
  }
}
// xdoc section -- end
