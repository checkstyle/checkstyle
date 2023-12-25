/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/
// violation 7 lines above 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected'
package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

public class Example3 { // ⤶
} // ␍⤶
