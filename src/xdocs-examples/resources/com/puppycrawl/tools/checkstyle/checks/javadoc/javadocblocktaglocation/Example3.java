/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation">
      <property name="tags" value="return, apiNote"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

class Example3 {
  // xdoc section -- start
  /**
   * Returns the result. @return the result
   * // violation above 'The Javadoc block tag '@return' should be placed'
   * Implementation note. @apiNote use this method carefully
   * // violation above 'The Javadoc block tag '@apiNote' should be placed'
   */
  int method() {
    return 1;
  }
  // xdoc section -- end
}
