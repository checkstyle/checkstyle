/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="&lt;br\s*[/]?&gt;|aka"/>
      <property name="ignoreMarkup" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpIgnoreMarkupFalse {

    // violation below 'Javadoc content matches'
    /**
     * Uses an HTML line break. <br>
     */
    void invalidHtmlTag() {
    }

    // violation below 'Javadoc content matches'
    /**
     * See <a href="https://example.com/aka">documentation</a>.
     */
    void invalidHrefInRawSource() {
    }

    /**
     * Uses regular visible text.
     */
    void validText() {
    }
}
