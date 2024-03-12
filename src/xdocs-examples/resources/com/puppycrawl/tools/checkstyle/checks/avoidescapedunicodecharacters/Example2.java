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
  // violation below, μs is a printable character. 'should be avoided.'
  String unitAbbrev1 = "\u03bcs";
  // violation below
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below
  String unitAbbrev3 = "\u03bcs";
  // violation below
  String unitAbbrev4 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // OK, non-printable control character.
    return '\ufeff' + content;
  }
}
// xdoc section -- end
