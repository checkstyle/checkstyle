/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParagraph"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// xdoc section -- start
// violation 5 lines below 'redundant paragraph tag found'
/**
 * This example shows redundant paragraph tag usage.
 *
 * <p>This paragraph is ok.</p>
 * <p><p>Nested redundant paragraph tag (violation).</p></p>
 *
 * <p>Another paragraph with redundant <p> tag inside (violation).
 *
 */
// violation 5 lines above 'redundant paragraph tag found'
public class Example6 {
}
// xdoc section -- end
