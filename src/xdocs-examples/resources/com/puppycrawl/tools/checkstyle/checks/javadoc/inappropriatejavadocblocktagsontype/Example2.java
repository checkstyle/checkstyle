/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnType"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsontype;

// xdoc section - start
/**
 * @param <T> a value
 */
class Example2<T> {
}
// xdoc section - end
