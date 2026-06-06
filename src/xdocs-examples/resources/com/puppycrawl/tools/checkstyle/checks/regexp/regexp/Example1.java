/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="// This code is copyrighted\."/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// (c) MyCompany

// (c) MyCompany

// xdoc section -- start
public class Example1 { // This code is copyrighted.

  private void foo() {
    System.out.println("");
    // System.out.println("debug");
    // fix me.
    // fix me.
  }
}
// xdoc section -- end
