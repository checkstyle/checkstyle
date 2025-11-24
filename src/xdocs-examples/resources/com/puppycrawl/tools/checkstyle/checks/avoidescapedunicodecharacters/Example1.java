/*xml
<module name="Checker">
    <module name="TreeWalker">
        <module name="AvoidEscapedUnicodeCharacters"/>
    </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

// xdoc section -- start
public class Example1 {
  // ok, perfectly clear even without a comment.
  String unitAbbrev = "μs";
  // violation below, the reader has no idea what this is. 'should be avoided.'
  String unitAbbrev1 = "\u03bcs";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev2 = "\u03bc\u03bc\u03bc";
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev3 = "\u03bcs"; // it is  μs
  // violation below, 'Unicode escape(s) usage should be avoided.'
  String unitAbbrev4 = "\u03bc\u03bcs";
  public static int content() {
    char content = 'r';
    // violation below, 'Unicode escape(s) usage should be avoided.'
    return '\ufeff' + content;
  }
}
// xdoc section -- end
