/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BlockCommentEndPosition">
      <property name="strategy" value="alone"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.blockcommentendposition;

// xdoc section -- start
/** violation because singleline is not allowed. */
class Example2 {
  // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

  /**
   * This Javadoc cause violation because block comment end
   * is not alone on the line. */
  int n = 10;
  // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

  /**
   * This is ok because block comment is alone on the line.
   *
   * @param a the first number
   * @param b the second number
   * @return the sum of a and b
   */
  int add(int a, int b) {
    return a + b;
  }
}
// xdoc section -- end
