/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharacters5 {
    private String a = "🎄"; // violation below
    private String b = "\uD83E\uDD73abs😀";
    private String c = "\uD83C\uDF84😀\uD83C\uDF84"; // OK, allowed by trailing comment
    // violation below
    private String d = "\uD83C\uDF84😀\uD83C\uDF84asdas\uD83C\uDF84abcd";

    public Object fooEmoji() {
        String unitAbbrev = "Î¼sð😀"; // violation below
        String unitAbbrev2 = "\u03bc😀";
        String unitAbbrev3 = "😀\u03bcs"; // Greek letter mu, "s"
        String fakeUnicode2 = "\\u23\\u123i\\u😀";
        String content = null;
        return "😀" + content + "\u03bc"; /* OK, allowed by trailing comment */
    }

    public boolean matches(String c) {
        switch (c) {
            //violation below
            case "\u03bc🎄":
            case "🎄\u03bc": /* OK, allowed by trailing comment */
                // violation below
            case "\t\u2028":
                // violation below
            case "\n😂\u3000":
                return true;
                // violation below
            case "😂\uD83D\uDE02":
                return false;
            default:
                return c.equals("\u2000😂\u2000"); // OK, allowed by trailing comment
        }
    }
}
