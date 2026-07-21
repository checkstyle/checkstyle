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
   * Returns the result. @return the result
   * // violation above 'The Javadoc block tag '@return' should be placed'
   * Implementation note. @apiNote use this method carefully
   * // ok, apiNote is not a default tag
   */
  int method() {
    return 1;
  }
  // xdoc section -- end
}
