/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BlockCommentEndPosition"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.blockcommentendposition;

// xdoc section -- start
/**
 * This multi-line Javadoc is ok because
 * block comment end alone on the line.
 */
class Example1 {

  /** This Javadoc is ok because single-line javadoc is allowed. */
  int n = 10;

  /**
   * This Javadoc causes violation because
   * block comment is not alone on the line.
   *
   * @param a the first number
   * @param b the second number
   * @return the sum of a and b */
  int add(int a, int b) {
    // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'
    return a + b;
  }
}
// xdoc section -- end
