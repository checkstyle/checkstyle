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
  // ok, a normal String below
  String unitAbbrev = "μs";
  // violation below, not all characters are escaped ('s'). 'should be avoided.'
  String unitAbbrev1 = "\u03bcs";
  // ok, because below are escape characters and allowIfAllCharacters = true.
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev3 = "\u03bcs"; // it is  μs
  // violation below, not all characters are escaped ('s'). 'should be avoided.'
  String unitAbbrev4 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // ok, all control characters are escaped below
    return '\ufeff' + content;
  }
}
// xdoc section -- end
