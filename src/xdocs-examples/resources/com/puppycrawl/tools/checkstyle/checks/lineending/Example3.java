/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="cr"/>
    <property name="fileExtensions" value="java"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example3 {  // ␊ // violation 'Expected line ending for file is CR, but LF is detected.'
  public void method() { // ␊ // violation 'Expected line ending for file is CR, but LF is detected.'
    int x = 1; // ␊           // violation 'Expected line ending for file is CR, but LF is detected.'
  } // ␊                      // violation 'Expected line ending for file is CR, but LF is detected.'
} // ␊                        // violation 'Expected line ending for file is CR, but LF is detected.'
// xdoc section -- end
