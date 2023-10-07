/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidEscapedUnicodeCharacters"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

// xdoc section -- start
public class Example1 {
  String unitAbbrev = "μs";     // OK, perfectly clear even without a comment.
  //for the below line the reader has no idea what this is.
  String unitAbbrev2 = "\u03bcs";// violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;    //violation
    // OK, an example of non-printable,
    // control characters (byte order mark).
  }
}
// xdoc section -- end
