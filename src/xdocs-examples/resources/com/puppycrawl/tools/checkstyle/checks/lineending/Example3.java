/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="crlf"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending;
// xdoc section -- start
public class Example3 {  // ␍␊ // violation 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected.'
  public void method() { // ␍␊ // violation 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected.'
    int x = 1; // ␍␊           // violation 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected.'
  } // ␍␊                      // violation 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected.'
} // ␍␊                        // violation 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected.'
// xdoc section -- end
