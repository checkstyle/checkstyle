/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="ignorePattern" value="^ *\* *[^ ]+$"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

// xdoc section -- start
/**
 * This is a short Javadoc comment.
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
 */
class Example3 {

  void testMethod() {
    System.out.println("This is a short line.");
    // violation below, 'Line is longer than 80 characters'
    System.out.println("This line is long and exceeds the default limit of 80 characters.");
  }
}
// xdoc section -- end
