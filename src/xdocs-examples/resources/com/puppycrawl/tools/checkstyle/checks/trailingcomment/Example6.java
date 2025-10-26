/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment">
      <property name="legalComment" value="^ violation.*"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="TrailingCommentCheck"/>
      <property name="query" value="//BLOCK_COMMENT_BEGIN
        [./COMMENT_CONTENT[@text=' NOSONAR ' or @text=' NOPMD '
        or @text=' SUPPRESS CHECKSTYLE ']]"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example6 {
  int a; /* SUPPRESS CHECKSTYLE */
  int b; /* NOPMD */
  int c; /* NOSONAR */
  int d; /* there is violation because of illegal content */
  // violation above
}
// xdoc section -- end
