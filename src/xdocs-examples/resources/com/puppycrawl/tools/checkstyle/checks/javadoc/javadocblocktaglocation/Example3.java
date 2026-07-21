/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation">
      <!-- default tags -->
      <property name="tags" value="author, deprecated, exception, hidden"/>
      <property name="tags" value="param, provides, return, see, serial"/>
      <property name="tags" value="serialData, serialField, since, throws"/>
      <property name="tags" value="uses, version"/>
      <!-- additional tags used in the project -->
      <property name="tags" value="noinspection"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

class Example3 {
  // xdoc section -- start
  // violation 7 lines below 'The Javadoc block tag '@return' should be placed'
  // ok, apiNote is not configured
  // violation 7 lines below 'The Javadoc block tag '@noinspection' should be placed'
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
