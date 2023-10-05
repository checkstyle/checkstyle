/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocContentLocation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoccontentlocation;

class Example1 {

// xdoc section -- start
/** This comment causes a violation because it starts from the first line
 * and spans several lines.
 */
/**
 * This comment is OK because it starts from the second line.
 */
/** This comment is OK because it is on the single-line. */
// xdoc section -- end

}
