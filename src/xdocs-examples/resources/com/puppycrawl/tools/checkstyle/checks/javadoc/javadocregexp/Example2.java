/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="&lt;/p&gt;"/>
      <property name="ignoreCase" value="false"/>
      <property name="ignoreMarkup" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

// xdoc section - start
class Example2 {

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * <p>See <a href="https://example.com/aka">documentation</a>.</p>
   */
  void first() {}

  // ok, raw source has no closing paragraph tag
  /**
   * Creates a user, AKA an account owner.
   */
  void second() {}

  // ok, raw source has no closing paragraph tag
  /**
   * Creates a user, aka an account owner.
   */
  void third() {}
}
// xdoc section - end
