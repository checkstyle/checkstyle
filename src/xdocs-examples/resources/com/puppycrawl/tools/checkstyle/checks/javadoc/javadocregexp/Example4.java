/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="aka"/>
      <property name="ignoreCase" value="true"/>
      <property name="ignoreMarkup" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

// xdoc section - start
class Example4 {

  // ok, rendered text has no aka
  /**
   * <p>See <a href="https://example.com/aka">documentation</a>.</p>
   */
  void first() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Creates a user, AKA an account owner.
   */
  void second() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Creates a user, aka an account owner.
   */
  void third() {}
}
// xdoc section - end
