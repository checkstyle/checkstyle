/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidEscapedUnicodeCharacters">
      <property name="allowEscapesForControlCharacters" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

// xdoc section -- start
public class Example2 {
  // OK, a normal String below
  String unitAbbrev = "μs";
  // violation below, "\u03bcs" is a printable character.
  String unitAbbrev2 = "\u03bcs"; // violation
  // violation below
  String unitAbbrev3 = "\u03bcs"; // it is μs
  public static int content() {
    char content = 'r';
    // OK, non-printable control character.
    return '\ufeff' + content;
  }
}
// xdoc section -- end
