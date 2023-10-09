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
  String unitAbbrev = "μs";      // OK, a normal String
  String unitAbbrev2 = "\u03bcs"; // OK, Greek letter mu, "s"
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;     // OK, an example of non-printable,
    // -----^--------------------- violation, comment is not used within same line.
  }
}

// xdoc section -- end
