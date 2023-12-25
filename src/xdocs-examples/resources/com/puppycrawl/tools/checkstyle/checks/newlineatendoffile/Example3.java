/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/
// violation 7 lines above 'ending for file is LF(\\n), but CRLF(\\r\\n) is'
package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;
// xdoc section -- start
public class Example3 { // ⤶
} // ␍⤶
// xdoc section -- end
