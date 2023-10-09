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
  String unitAbbrev = "μs";
  String unitAbbrev2 = "\u03bcs"; // violation
  String unitAbbrev3 = "\u03bc\u03bc\u03bc";
  String unitAbbrev4 = "\u03bc\u03bcs";// violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;
  }
}

// xdoc section -- end
