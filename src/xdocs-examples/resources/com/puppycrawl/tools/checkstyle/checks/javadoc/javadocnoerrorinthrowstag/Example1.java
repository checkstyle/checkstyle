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

  /**
   * Valid explicit Error.
   *
   * @throws OutOfMemoryError if memory is exhausted.
   */
  void validExplicitError() {
    throw new OutOfMemoryError("memory exhausted");
  }

  // violation 5 lines below """Error type 'StackOverflowError' should not be
  // documented in '@throws' tag."""
  /**
   * Invalid Javadoc.
   *
   * @throws StackOverflowError if recursion is too deep.
   */
  void invalidError() {
  }
}
// xdoc section - end
