/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidEscapedUnicodeCharacters">
      <property name="allowByTailComment" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

// xdoc section -- start
public class Example3 {
  // OK, a normal String below
  String unitAbbrev = "μs";
  // violation below
  String unitAbbrev1 = "\u03bcs";
  // violation below
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below
  String unitAbbrev3 = "\u03bcs";
  String unitAbbrev4 = "\u03bc\u03bcs"; // it is  μs
  public static int content() {
    char content = 'r';
    // violation below
    return '\ufeff' + content;
    // -----^--------------------- violation, comment is not used within same line.
  }
}
// xdoc section -- end
