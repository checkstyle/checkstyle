/*xml
<module name="Checker">
  <module name="NewlineAtEndOfFile"/>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.newlineatendoffile;

// xdoc section -- start
public class Example1 { // ⤶
// ⤶
} // ⤶

// File ending without a new line
class A { // ⤶
// ⤶
} // violation, the file does not end with a new line