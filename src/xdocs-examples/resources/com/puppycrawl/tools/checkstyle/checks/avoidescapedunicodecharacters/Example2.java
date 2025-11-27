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
  // ok, a normal String below
  String unitAbbrev = "μs";
  // violation below, μs is a printable character. 'should be avoided.'
  String unitAbbrev1 = "\u03bcs";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev3 = "\u03bcs";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev4 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // ok, non-printable control character.
    return '\ufeff' + content;
  }
}
// xdoc section -- end
