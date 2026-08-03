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
 * @return a value
 */
class Example1 {
  // violation above 'Invalid '@return' tag for 'Example1'.'
}
// xdoc section - end
