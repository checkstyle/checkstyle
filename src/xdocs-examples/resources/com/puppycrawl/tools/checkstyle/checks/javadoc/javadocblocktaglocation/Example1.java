/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

class Example1 {
  // xdoc section -- start
  /**
   * Escaped tag &#64;version is plain text.
   * Plain text with {@code @see} is ignored.
   * A @custom tag is not configured.
   * Returns the result. @return the result
   * Additional description keeps the example structure aligned.
   */
  // violation 4 lines above 'The Javadoc block tag '@return' should be placed'
  int method() {
    return 1;
  }
  // xdoc section -- end
}
