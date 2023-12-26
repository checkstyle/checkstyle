/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

// xdoc section -- start
public class Example2 { // ⤶
} // violation, file should end with a new line.
// xdoc section -- end