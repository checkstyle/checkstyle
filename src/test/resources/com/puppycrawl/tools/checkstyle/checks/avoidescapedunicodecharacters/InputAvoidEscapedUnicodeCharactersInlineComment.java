/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersInlineComment {

    void method() {
        String s = "\u221e"; /* comment */ int i = 0;
        // violation above, 'Unicode escape(s) usage should be avoided.'
    }
}
