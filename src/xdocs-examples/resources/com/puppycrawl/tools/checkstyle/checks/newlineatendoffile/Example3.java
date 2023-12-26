/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="fileExtensions" value="java, xml, py"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

// xdoc section -- start
public class Example3 { // ⤶
// ⤶
} // ␍⤶ // violation, expected line ending for file is LF(\n), but CRLF(\r\n) is detected
// xdoc section -- end
