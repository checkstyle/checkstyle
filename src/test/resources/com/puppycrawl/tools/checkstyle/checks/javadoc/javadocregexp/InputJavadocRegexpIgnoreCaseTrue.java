/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="AKA"/>
      <property name="ignoreCase" value="true"/>
      <property name="ignoreMarkup" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpIgnoreCaseTrue {

    // violation below 'Javadoc content matches'
    /**
     * Lowercase aka is matched.
     */
    void invalid() {
    }
}
