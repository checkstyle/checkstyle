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
  String unitAbbrev = "μs";
  String unitAbbrev2 = "\u03bcs"; // violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;
  }
}
// xdoc section -- end
