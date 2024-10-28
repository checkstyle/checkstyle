/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 4 lines below 'extra <p> tag after block-tag <ul>'
/**
 * Example of a <p> tag placed after a block-level element (violation).
 *
 * <ul>
 * <li>Item 1</li>
 * <li>Item 2</li>
 * <li>Item 3</li>
 * </ul>
 *
 * <p>Paragraph after the block element <ul> is not allowed (violation).</p>
 */
// violation 8 lines below 'extra <p> tag after block-tag <ul>'
public class Example8 {
}
// xdoc section -- end
