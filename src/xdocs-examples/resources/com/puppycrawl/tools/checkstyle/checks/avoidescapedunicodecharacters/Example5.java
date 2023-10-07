/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidEscapedUnicodeCharacters">
      <property name="allowNonPrintableEscapes" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

// xdoc section -- start
public class Example5 {
  String unitAbbrev = "μs";       // OK, a normal String
  // In the below line, printable escape character.
  String unitAbbrev1 = "\u03bcs"; // violation
  // In the below line, printable escape character.
  String unitAbbrev2 = "\u03bc\u03bc\u03bc"; // violation
  // In the below line, printable escape character.
  String unitAbbrev3 = "\u03bc\u03bcs";// violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;           // OK, non-printable escape character.
  }
}

// xdoc section -- end
