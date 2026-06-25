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

// (c) MyCompany

// (c) MyCompany

// xdoc section -- start
// This code is copyrighted.
public class Example7 {

  private void foo() {
    System.out.println(""); // violation, 'Line matches the illegal pattern'
    // System.out.println("debug");
    // fix me.
    // fix me.
  }
}
// xdoc section -- end
