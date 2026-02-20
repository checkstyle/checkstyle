/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoGetMessageInThrow"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

// xdoc section - start
class Example1 {
  void method1() throws IOException {
    try {
      throw new IOException();
    } catch (IOException ex) {
      // violation below, 'ex.getMessage()' is redundant
      throw new IOException("Error: " + ex.getMessage(), ex);
    }
  }

  void method2() throws IOException {
    try {
      throw new IOException();
    } catch (IOException ex) {
      // OK, different thrown type
      throw new IllegalStateException("Error: " + ex.getMessage(), ex);
    }
  }

  void method3() throws IOException {
    try {
      throw new IOException();
    } catch (IOException ex) {
      // OK, no getMessage() on caught exception
      throw new IOException("Error processing file", ex);
    }
  }
}
// xdoc section - end
