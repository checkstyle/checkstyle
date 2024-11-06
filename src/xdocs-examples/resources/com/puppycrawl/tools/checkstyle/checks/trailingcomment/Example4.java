/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TrailingComment"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="TrailingCommentCheck"/>
      <property name="query" value="//SINGLE_LINE_COMMENT
        [./COMMENT_CONTENT[starts-with(@text, ' NOPMD')]]"/>
      <property name="query" value="//SINGLE_LINE_COMMENT
        [./COMMENT_CONTENT[starts-with(@text, ' SUPPRESS CHECKSTYLE')]]"/>
      <property name="query" value="//SINGLE_LINE_COMMENT
        [./COMMENT_CONTENT[starts-with(@text, ' NOSONAR')]]"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.trailingcomment;

// xdoc section -- start
public class Example4 {
  int a; // SUPPRESS CHECKSTYLE - OK, comment starts with " SUPPRESS CHECKSTYLE"
  int b; // NOPMD - OK, comment starts with " NOPMD"
  int c; // NOSONAR - OK, comment starts with " NOSONAR"
  int d; // violation, not suppressed
}
// xdoc section -- end
