/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonInTryWithResources"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonintrywithresources;

import java.io.IOException;
import java.io.PipedReader;
import java.io.Reader;

// xdoc section -- start
class Example1 {
  void method() throws IOException {
    try (Reader r1 = new PipedReader();) {} // violation, 'Unnecessary semicolon'
    try (Reader r4 = new PipedReader(); Reader r5 = new PipedReader()
         ;) {} // violation, 'Unnecessary semicolon'
    try (Reader r6 = new PipedReader();
         Reader r7
           = new PipedReader(); //closing parenthesis is not on the same line
    ) {}
  }
}
// xdoc section -- end
