/* // violation 'Expected line ending for file is LF(\\n), but CRLF(\\r\\n) is detected.'
NewlineAtEndOfFile
lineSeparator = LF
fileExtensions = (default)all files


*/

/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

public class Example3 { // ⤶
} // ␍⤶
