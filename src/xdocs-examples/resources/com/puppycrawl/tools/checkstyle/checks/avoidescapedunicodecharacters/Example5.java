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
  String unitAbbrev = "μs";
  String unitAbbrev1 = "\u03bcs"; // violation
  String unitAbbrev2 = "\u03bc\u03bc\u03bc"; // violation
  String unitAbbrev3 = "\u03bc\u03bcs";// violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;
  }
}

// xdoc section -- end
