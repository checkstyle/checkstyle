/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="cr"/>
  </module>
</module>
*/
// violation 7 lines above 'File does not end with a newline 'cr'.'
package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;
// xdoc section -- start
public class Example7 { // ⤶
} // no ⤶ below it is violation
// xdoc section -- end