/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnPackage"/>
  </module>
</module>
*/

// xdoc section - start
/**
 * Invalid Javadoc comment
 *
 * @param value This doesn't exist
 * @return
 */
package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsonpackage;
// 2 violations above:
// 'Invalid '@param' tag for 'package'.'
// 'Invalid '@return' tag for 'package'.'
// xdoc section - end
