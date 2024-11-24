/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="max" value="120"/>
  </module>
</module>
*/
// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

/**
 * This is a short Javadoc comment.
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
 */
class Example2 {

  void testMethod(String str) {
    str = MSG_KEY;
    System.out.println("This is a short line.");

    System.out.println("This line is long and exceeds the default limit of 80 characters.");
  }
}
// xdoc section -- end
