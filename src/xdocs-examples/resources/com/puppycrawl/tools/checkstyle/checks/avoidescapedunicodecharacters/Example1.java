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
  // OK, perfectly clear even without a comment.
  String unitAbbrev = "μs";
  //for the below line the reader has no idea what this is.
  // violation below
  String unitAbbrev2 = "\u03bcs";
  //violation below
  String unitAbbrev3 = "\u03bcs"; // it is  μs
  public static int content() {
    char content = 'r';
    //violation below
    return '\ufeff' + content;
  }
}
// xdoc section -- end
