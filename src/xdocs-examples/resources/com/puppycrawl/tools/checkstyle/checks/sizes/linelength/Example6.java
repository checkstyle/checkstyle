/*xml
<module name="Checker">
  <module name="LineLength">
    <property name="max" value="84"/>
  </module>
  <module name=
      "com.puppycrawl.tools.checkstyle.filters.SuppressWithPlainTextCommentFilter">
    <property name="checkFormat" value="LineLength"/>
    <property name="offCommentFormat" value='^.*"""$'/>
    <property name="onCommentFormat" value='^\s*"""\s*(?:[,;]|.+)$'/>
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
class Example6 {

  void testMethod(String str) {
    str = MSG_KEY;
    System.out.println("This is a short line.");
    // violation below 'Line is longer than 84 characters (found 92).'
    System.out.println("This line is long and exceeds the default limit of 80 characters.");
    // filtered violation below 'Line length is 90, expected 84'
    String str1 = """
        This is a very really long string that exceeds the limit but no error............
        """; // SUPPRESS CHECKSTYLE LineLength
    // filtered violation below 'Line length is 90, expected 84'
    String str2 =
        """
        This is a very really long string that exceeds the limit but no error............
        """;// SUPPRESS CHECKSTYLE LineLength
  }
}
// xdoc section -- end
