/*xml
<module name="Checker">
  <module name="LineEnding">
    <property name="lineEnding" value="crlf"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.lineending; // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
// xdoc section -- start // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
public class Example4 { // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
    public void method() { // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
        int x = 1; // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
    } // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
} // ␊ // violation 'Expected line ending for file is CRLF(\\r\\n), but LF(\\n) is detected.'
// xdoc section -- end