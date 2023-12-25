/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile">
    <property name="fileExtensions" value="java, xml, py"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

// xdoc section -- start
// Any java file
public class Example3 { // ⤶
} // violation, file should end with a new line.

// Any py file
// print("Hello World") // violation, file should end with a new line.

// Any txt file
// This is a sample text file. // ok, this file is not specified in the config.
// xdoc section -- end