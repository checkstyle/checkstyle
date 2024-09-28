/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
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
 * <p>No blank line before the tag (violation).
 *
 * <p>
 * New line after tag (violation).
 *
 * <p> Whitespace after tag (violation).
 *
 */
// violation 3 lines above 'tag should be placed immediately before the first word'
public class Example1 {
}
// xdoc section -- end
