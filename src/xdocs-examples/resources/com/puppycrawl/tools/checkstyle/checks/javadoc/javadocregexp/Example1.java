/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

// xdoc section - start
class Example1 {

  // ok, default settings do not perform any validation
  /**
   * <p>See <a href="https://example.com/aka">documentation</a>.</p>
   */
  void first() {}

  // ok, default settings do not perform any validation
  /**
   * Creates a user, AKA an account owner.
   */
  void second() {}

  // ok, default settings do not perform any validation
  /**
   * Creates a user, aka an account owner.
   */
  void third() {}
}
// xdoc section - end
