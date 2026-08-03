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
class Example1 {

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
}
// xdoc section - end
