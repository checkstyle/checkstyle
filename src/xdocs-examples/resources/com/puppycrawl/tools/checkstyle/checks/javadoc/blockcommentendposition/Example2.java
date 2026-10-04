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

// xdoc section - start
/** Singleline Javadoc. */
class Example2 { // violation above ''BLOCK_COMMENT_END' must be on the new line.'

  /**
   * Invalid block comment end position. */
  int n = 10;
  // violation 2 lines above ''BLOCK_COMMENT_END' must be on the new line.'

  /**
   * Valid block comment end position.
   *
   * @param a the first number
   * @param b the second number
   * @return the sum of a and b
   */
  int add(int a, int b) {
    return a + b;
  }
}
// xdoc section - end
