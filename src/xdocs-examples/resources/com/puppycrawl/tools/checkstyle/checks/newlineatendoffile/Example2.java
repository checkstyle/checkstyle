/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

// xdoc section -- start
// File ending with a new line
public class Example2 {// ⤶
// ⤶
}// ⤶

// File ending without a new line
class B { // ⤶
// ⤶
} // ␍⤶ // violation, expected line ending for file is LF(\n), but CRLF(\r\n) is detected