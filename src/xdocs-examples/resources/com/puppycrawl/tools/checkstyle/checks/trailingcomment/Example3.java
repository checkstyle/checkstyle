/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="TrailingCommentCheck"/>
      <property name="query" value="//SINGLE_LINE_COMMENT
        [./COMMENT_CONTENT[@text=' NOSONAR\n' or @text=' NOPMD\n'
        or @text=' SUPPRESS CHECKSTYLE\n']]"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example3 {
  int a; // SUPPRESS CHECKSTYLE
  int b; // NOPMD
  int c; // NOSONAR
  int d; // violation, not suppressed
}
// xdoc section -- end
