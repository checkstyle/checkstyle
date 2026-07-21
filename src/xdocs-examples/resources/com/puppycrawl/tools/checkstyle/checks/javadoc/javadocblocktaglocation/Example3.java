/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation">
      <property name="tags" value="author, deprecated, exception, hidden, param, provides, return, see, serial, serialData, serialField, since, throws, uses, version, noinspection"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

class Example3 {
  // xdoc section -- start
  /**
   * Escaped tag &#64;version is plain text.
   * Plain text with {@code @see} is ignored.
   * A @custom tag is not configured.
   * Returns the result. @return the result
   * Suppression note. @noinspection unused
   */
  // violation 3 lines above 'The Javadoc block tag '@return' should be placed'
  int method() {
    return 1;
  }
  // violation 6 lines above 'The Javadoc block tag '@noinspection' should be placed'
  // xdoc section -- end
}
