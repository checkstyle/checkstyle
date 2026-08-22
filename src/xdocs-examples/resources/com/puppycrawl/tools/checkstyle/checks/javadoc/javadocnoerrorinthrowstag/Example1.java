/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocNoErrorInThrowsTag"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocnoerrorinthrowstag;

import java.io.IOException;

// xdoc section - start
class Example1 {
  /**
   * Valid Javadoc.
   *
   * @throws IOException if an input or output exception occurs.
   * @throws IllegalArgumentException if the argument is invalid.
   */
  void validExceptions() throws IOException {
  }

  // violation 5 lines below """Error type 'OutOfMemoryError' should not be
  // documented in '@throws' tag."""
  /**
   * Invalid Javadoc.
   *
   * @throws OutOfMemoryError if memory is exhausted.
   */
  void invalidError() {
  }
}
// xdoc section - end
