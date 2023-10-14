/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidEscapedUnicodeCharacters">
      <property name="allowIfAllCharactersEscaped" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

// xdoc section -- start
public class Example4 {
  // OK, a normal String below
  String unitAbbrev = "μs";
  // violation below, not all characters are escaped ('s').
  String unitAbbrev2 = "\u03bcs"; // violation
  // violation below
  String unitAbbrev3 = "\u03bcs"; // it is  μs
  String unitAbbrev4 = "\u03bc\u03bc\u03bc";
  // violation below, not all characters are escaped ('s').
  String unitAbbrev5 = "\u03bc\u03bcs"; // violation
  public static int content() {
    char content = 'r';
    // OK, all control characters are escaped below
    return '\ufeff' + content;
  }
}
// xdoc section -- end
