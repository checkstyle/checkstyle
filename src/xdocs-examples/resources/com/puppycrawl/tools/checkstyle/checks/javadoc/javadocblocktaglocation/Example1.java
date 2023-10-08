/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocBlockTagLocation"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

class Example1 {
// xdoc section -- start
/**
 * Escaped tag &#64;version (OK)
 * Plain text with {@code @see} (OK)
 * A @custom tag (OK)
 *
 */
// xdoc section -- end

}
