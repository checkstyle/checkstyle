/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 4 lines below 'HTML element not closed properly'
/**
 * This example demonstrates a missed HTML close violation.
 *
 * <p>Unclosed <b>bold tag (violation)
 *
 * <p>Correctly closed <i>italic tag</i> (ok).
 */
// violation 4 lines above 'HTML element not closed properly'
public class Example5 {
}
// xdoc section -- end
