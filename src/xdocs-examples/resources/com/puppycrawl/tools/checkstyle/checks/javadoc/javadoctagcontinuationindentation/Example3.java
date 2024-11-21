/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation">
      <property name="violateExecutionOnNonTightHtml" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// xdoc section -- start
/**
 * <p> 'p' tag is unclosed
 * <p> 'p' tag is closed</p>
 */
public class Example3 {
  // violation 4 lines above 'Unclosed HTML tag found: p'

}
// xdoc section -- end
