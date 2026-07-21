/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation">
      <property name="tags" value="apiNote, implSpec, implNote"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

class Example2 {
  // xdoc section -- start
  /**
   * Escaped tag &#64;version is plain text.
   * Plain text with {@code @see} is ignored.
   * A @custom tag is not configured.
   * Returns the result. @return the result
   * Implementation note. @apiNote use this method carefully
   * Suppression note. @noinspection unused
   */
  // ok, return is not configured
  // violation 4 lines above 'The Javadoc block tag '@apiNote' should be placed'
  // ok, noinspection is not configured
  int method() {
    return 1;
  }
  // xdoc section -- end
}
