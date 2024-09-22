/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph">
      <property name="allowNewlineParagraph" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 5 lines below '<p> tag should be preceded with an empty line'
/**
 * No tag (ok).
 *
 * <p>Tag immediately before the text (ok).
 * <p>No blank line before the tag.
 *
 * <p>
 * New line after tag (ok).
 *
 * <p> Whitespace after tag (ok).
 *
 */
public class Example2 {
}
// xdoc section -- end
