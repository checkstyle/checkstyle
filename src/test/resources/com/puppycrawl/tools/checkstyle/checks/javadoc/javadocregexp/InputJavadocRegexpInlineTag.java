/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="temporary|beta"/>
      <property name="ignoreCase" value="false"/>
      <property name="ignoreMarkup" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpInlineTag {

    // violation below 'Javadoc content matches'
    /**
     * Returns {@code temporary} value.
     */
    void invalidCodeTag() {
    }

    // violation below 'Javadoc content matches'
    /**
     * Returns {@literal beta} value.
     */
    void invalidLiteralTag() {
    }

    /**
     * Returns stable value.
     */
    void validInlineTag() {
    }
}
