/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="System\.out\.println"/>
      <property name="illegalPattern" value="true"/>
      <property name="message" value="Avoid using System.out.println"/>
    </module>
  </module>
</module>
*/

// violation 6 lines above 'Avoid using System...'
package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// xdoc section -- start
// (c) MyCompany
// (c) MyCompany
// This code is copyrighted.
public class Example4 {
  private void foo() { // violation below 'Avoid using System.out...'
    System.out.println("");
    // System.out.println("debug");
    // fix me.
    // fix me.
  } // violation 3 lines above 'Avoid using System.out...'
}
// xdoc section -- end
