/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="lang=&quot;e\.g\.&quot;|forbidden\s*="/>
      <property name="ignoreCase" value="false"/>
      <property name="ignoreMarkup" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpSnippetTag {

    // violation below 'Javadoc content matches'
    /**
     * {@snippet lang="e.g." :
     *   int value = 10;
     * }
     */
    void invalidSnippetAttribute() {
    }

    // violation below 'Javadoc content matches'
    /**
     * {@snippet :
     *   int forbidden = 10;
     * }
     */
    void invalidSnippetBody() {
    }

    /**
     * {@snippet :
     *   int value = 20;
     * }
     */
    void validSnippetTag() {
    }
}
