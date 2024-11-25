/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="ignorePattern"
              value="^(import) "/>
    <property name="max" value="60"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;
// violation above, 'Line is longer than 60 characters'
import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

/**
 * This is a short Javadoc comment.
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
 */
class Example5 {
  // violation 3 lines above 'Line is longer'
  void testMethod(String str) {
    str = MSG_KEY;
    System.out.println("This is a short line.");
    // violation below, 'Line is longer than 60 characters'
    System.out.println("This line is long and exceeds the default limit of 80 characters.");
  }
}
// xdoc section -- end
