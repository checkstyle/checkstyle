/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation">
      <property name="offset" value="2"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// xdoc section -- start
/**
 * @tag comment
 * Indentation spacing is 0
 *   Indentation spacing is 2
 *  Indentation spacing is 1
 */
class Example2 {
  // violation 5 lines above 'Line continuation have incorrect indentation level'
  // violation 4 lines above 'Line continuation have incorrect indentation level'
}
// xdoc section -- end
