/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocRegexp">
      <property name="format" value="^first second"/>
      <property name="ignoreMarkup" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocregexp;

public class InputJavadocRegexpTextSplitAcrossLines {

    // violation below 'Javadoc content matches'
    /**first
second*/
    void invalidTextSplitAcrossLines() {
    }
}
