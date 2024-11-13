/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireEmptyLineBeforeBlockTagGroup"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.requireemptylinebeforeblocktaggroup;

// xdoc section -- start
class Example1 {
  /**
   * ValidMethod's javadoc.
   *
   * @return something
   */
  boolean methodWithValidJavadoc() {
    return false;
  }
  /**
   * InvalidMethod's javadoc.
   * @return something
   */ // violation above, ''@return' should be preceded with an empty line'
  boolean methodWithInvalidJavadoc() {
    return false;
  }
}
// xdoc section -- end
