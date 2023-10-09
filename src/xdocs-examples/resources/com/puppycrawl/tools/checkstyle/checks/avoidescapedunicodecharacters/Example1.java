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
  String unitAbbrev = "μs";
  String unitAbbrev2 = "\u03bcs";// violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content; // violation
  }
}
// xdoc section -- end
