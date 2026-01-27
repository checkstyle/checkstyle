/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoGetMessageInCatchThrow"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

// xdoc section -- start
class Example1 {
  void method1() throws IOException {
    try {
      throw new IOException();
    } catch (IOException ex) {
      // violation below, 'ex.getMessage()' is redundant
      throw new IllegalStateException("Error: " + ex.getMessage(), ex);
    }
  }

  void method2() throws IOException {
    try {
      throw new IOException();
    } catch (IOException ex) {
      // OK, provides context without redundant message
      throw new IllegalStateException("Error processing file", ex);
    }
  }
}
// xdoc section -- end
