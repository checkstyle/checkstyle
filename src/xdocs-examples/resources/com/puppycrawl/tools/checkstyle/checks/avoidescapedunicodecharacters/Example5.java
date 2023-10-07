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
  // OK, a normal String below
  String unitAbbrev = "μs";
  // In the below line, printable escape character.
  // violation below
  String unitAbbrev1 = "\u03bcs";
  // In the below line, printable escape character.
  // violation below
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below
  String unitAbbrev3 = "\u03bcs"; // it is μs
  // violation below
  String unitAbbrev4 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // OK, non-printable escape character below
    return '\ufeff' + content;
  }
}

// xdoc section -- end
