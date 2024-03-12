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
  // OK, a normal String below.
  String unitAbbrev = "μs";
  // violation below, printable escape character. 'should be avoided.'
  String unitAbbrev1 = "\u03bcs";
  // violation below, printable escape character. 'should be avoided.'
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
