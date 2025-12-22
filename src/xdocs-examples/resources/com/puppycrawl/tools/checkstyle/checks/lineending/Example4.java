/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="crlf"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example4 { // ␊
  public void method() { // ␊ // violation
    int x = 1; // ␊ // violation
  } // ␊ // violation
} // ␊ // violation
// xdoc section -- end
