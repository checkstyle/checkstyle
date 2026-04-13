/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="crlf"/>
  </module>
</module>
*/
// violation 7 lines above 'File does not end with a newline 'crlf'.'
package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;
// xdoc section -- start
public class Example8 { // ⤶
} // no ⤶ below it is violation
// xdoc section -- end