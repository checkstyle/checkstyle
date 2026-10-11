/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocThrowsOrder"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocthrowsorder;

import java.io.IOException;

// xdoc section - start
class Example1 {
  /**
   * Correct order.
   * @throws IllegalArgumentException if input is invalid.
   * @exception NullPointerException if input is null.
   */
  void validOrder() {}

  // violation 5 lines below """@throws tag for 'IOException' should be
  // placed alphabetically before 'SecurityException'."""
  /**
   * Incorrect order.
   * @exception SecurityException if security check fails.
   * @throws IOException if an I/O error occurs.
   */
  void invalidOrder() throws IOException {}
}
// xdoc section - end
