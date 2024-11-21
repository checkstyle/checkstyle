/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocTagContinuationIndentation"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

// xdoc section -- start
/**
 * @tag comment
 *  Indentation spacing is 1
 *   Indentation spacing is 2
 *     Indentation spacing is 4
 */
class Example1 {
  // violation 5 lines above 'Line continuation have incorrect indentation level'
  // violation 5 lines above 'Line continuation have incorrect indentation level'
}
// xdoc section -- end
