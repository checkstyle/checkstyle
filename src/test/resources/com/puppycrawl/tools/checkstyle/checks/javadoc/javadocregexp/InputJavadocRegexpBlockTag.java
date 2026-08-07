/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="legacy|external"/>
      <property name="ignoreCase" value="true"/>
      <property name="ignoreMarkup" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpBlockTag {

    // violation below 'Javadoc content matches'
    /**
     * Creates a value.
     *
     * @deprecated use legacy factory
     */
    void invalidDeprecatedTag() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Creates a value.
     *
     * @throws IllegalStateException when external state is used
     */
    void invalidThrowsTag() {
    }

    /**
     * Creates a value.
     *
     * @deprecated use replacement factory
     */
    void validBlockTag() {
    }
}
