/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="(?i)fix me\."/>
      <property name="illegalPattern" value="true"/>
      <property name="errorLimit" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;

// (c) MyCompany

// (c) MyCompany

// xdoc section -- start
public class Example10 { // This code is copyrighted.

  private void foo() {
    System.out.println("");
    // System.out.println("debug");
    // fix me. // violation 'Line matches the illegal pattern'
    // fix me.
  }
}
// xdoc section -- end
