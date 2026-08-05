/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="https://example.com/aka"/>
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
   * See <a href="https://example.com/aka">documentation</a>.
   */
  void first() {}

  // ok, raw text has no URL
  /**
   * Creates a user, AKA an account owner.
   */
  void second() {}
}
// xdoc section - end
