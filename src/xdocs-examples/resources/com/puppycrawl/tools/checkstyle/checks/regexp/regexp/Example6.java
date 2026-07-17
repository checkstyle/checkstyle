/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Regexp">
      <property name="format" value="System\.out\.println"/>
      <property name="illegalPattern" value="true"/>
      <property name="ignoreComments" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.regexp.regexp;
// xdoc section -- start
// (c) MyCompany
// (c) MyCompany
// This code is copyrighted.
public class Example6 {
  private void foo() {  // violation below 'Line matches the illegal pattern'
    System.out.println("");
    // System.out.println("debug");
    // fix me.
    // fix me.
  }
}
// xdoc section -- end
