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
  // ok, a normal String below.
  String unitAbbrev = "μs";
  // violation below, printable escape character. 'should be avoided.'
  String unitAbbrev1 = "\u03bcs";
  // violation below, printable escape character. 'should be avoided.'
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev3 = "\u03bcs"; // it is μs
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev4 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // ok, non-printable escape character below
    return '\ufeff' + content;
  }
}
// xdoc section -- end
