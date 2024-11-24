/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="max" value="120"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

// xdoc section -- start
/**
 * This is a short Javadoc comment.
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
 */
class Example2 {

  void testMethod() {
    System.out.println("This is a short line.");

    System.out.println("This line is long and exceeds the default limit of 80 characters.");
  }
}
// xdoc section -- end
