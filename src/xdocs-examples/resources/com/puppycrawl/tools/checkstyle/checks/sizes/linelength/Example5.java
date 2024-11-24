/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="ignorePattern" value="^$"/>
    <property name="max" value="60"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;
// violation above, 'Line is longer than 60 characters'
/**
 * This is a short Javadoc comment.
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
 */
class Example5 {
  // violation 3 lines above 'Line is longer'
  void testMethod() {
    System.out.println("This is a short line.");
    // violation below, 'Line is longer than 60 characters'
    System.out.println("This line is long and exceeds the default limit of 80 characters.");
  }
}
// xdoc section -- end
