/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="fix me\."/>
      <property name="illegalPattern" value="true"/>
      <property name="errorLimit" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// xdoc section -- start
// (c) MyCompany
// (c) MyCompany
// This code is copyrighted.
public class Example5 {
  private void foo() {
    System.out.println("");
    // System.out.println("debug");
    // fix me.
    // fix me.
  } // violation 2 lines above 'The error limit has been exceeded'
}
// xdoc section -- end
