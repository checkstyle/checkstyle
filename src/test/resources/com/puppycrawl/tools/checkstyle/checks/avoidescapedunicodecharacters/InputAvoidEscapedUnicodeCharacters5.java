/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharacters5 {
    private String a = "🎄";// violation below, 'Unicode escape(s) usage should be avoided.'
    private String b = "\uD83E\uDD73abs😀";
    private String c = "\uD83C\uDF84😀\uD83C\uDF84"; // ok, allowed by trailing comment
    // violation below, 'Unicode escape(s) usage should be avoided.'
    private String d = "\uD83C\uDF84😀\uD83C\uDF84asdas\uD83C\uDF84abcd";

    public Object fooEmoji() {
        String unitAbbrev = "Î¼sð😀";
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev2 = "\u03bc😀";
        String unitAbbrev3 = "😀\u03bcs"; // Greek letter mu, "s"
        String fakeUnicode2 = "\\u23\\u123i\\u😀";
        String content = null;
        return "😀" + content + "\u03bc"; /* OK, allowed by trailing comment */
    }

    public boolean matches(String c) {
        switch (c) {
        // violation below, 'Unicode escape(s) usage should be avoided.'
            case "\u03bc🎄":
            case "🎄\u03bc": /* OK, allowed by trailing comment */
        // violation below, 'Unicode escape(s) usage should be avoided.'
            case "\t\u2028":
        // violation below, 'Unicode escape(s) usage should be avoided.'
            case "\n😂\u3000":
                return true;
        // violation below, 'Unicode escape(s) usage should be avoided.'
            case "😂\uD83D\uDE02":
                return false;
            default:
                return c.equals("\u2000😂\u2000"); // ok, allowed by trailing comment
        }
    }
}
