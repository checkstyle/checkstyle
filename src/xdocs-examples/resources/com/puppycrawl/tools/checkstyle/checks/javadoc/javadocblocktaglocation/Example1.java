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
  // violation 7 lines below 'The Javadoc block tag '@return' should be placed'
  // ok, apiNote is not configured
  // ok, noinspection is not configured
  /**
   * Escaped tag &#64;version is plain text.
   * Plain text with {@code @see} is ignored.
   * A @custom tag is not configured.
   * Returns the result. @return the result
   * Implementation note. @apiNote use this method carefully
   * Suppression note. @noinspection unused
   */
  int method() {
    return 1;
  }
  // xdoc section -- end
}
