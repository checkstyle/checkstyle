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
  String unitAbbrev = "μs";      // OK, a normal String
  //In the below line not all characters are escaped ('s').
  String unitAbbrev2 = "\u03bcs"; // violation
  String unitAbbrev3 = "\u03bc\u03bc\u03bc"; // OK
  //In the below line not all characters are escaped ('s').
  String unitAbbrev4 = "\u03bc\u03bcs";// violation
  public static int content() {
    char content = 'r';
    return '\ufeff' + content;// OK, all control characters are escaped
  }
}

// xdoc section -- end
