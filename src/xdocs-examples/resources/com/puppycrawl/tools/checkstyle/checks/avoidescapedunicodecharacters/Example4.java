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
public class Example4 {
  // OK, a normal String below
  String unitAbbrev = "μs";
  //In the below line not all characters are escaped ('s').
  // violation below
  String unitAbbrev2 = "\u03bcs";
  // violation below
  String unitAbbrev3 = "\u03bcs"; // it is  μs
  // violation below
  String unitAbbrev4 = "\u03bc\u03bc\u03bc";
  //In the below line not all characters are escaped ('s').
  // violation below
  String unitAbbrev5 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // OK, all control characters are escaped below
    return '\ufeff' + content;
  }
}

// xdoc section -- end
