/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 4 lines below '<p> tag should not precede HTML block-tag <div>'
/**
 * Example showing a block tag preceded by a <p> tag.
 *
 * <p><div>Block tag 'div' should not follow a <p> tag directly (violation).</div>
 *
 * <p><span>Inline tag 'span' after <p> tag is allowed (ok).</span>
 */
// violation 4 lines above '<p> tag should not precede HTML block-tag <div>'
public class Example7 {
}
// xdoc section -- end
