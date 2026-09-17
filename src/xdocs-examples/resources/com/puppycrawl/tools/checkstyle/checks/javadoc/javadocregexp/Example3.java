/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="(^|\W)(AKA)(\W|$)"/>
      <property name="ignoreCase" value="false"/>
      <property name="ignoreMarkup" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

// xdoc section - start
class Example3 {

  // ok, rendered text has no AKA
  /**
   * <p>See <a href="https://example.com/aka">documentation</a>.</p>
   */
  void first() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Creates a user, AKA an account owner.
   */
  void second() {}

  // ok, rendered text has no AKA
  /**
   * Creates a user, aka an account owner.
   */
  void third() {}
}
// xdoc section - end
