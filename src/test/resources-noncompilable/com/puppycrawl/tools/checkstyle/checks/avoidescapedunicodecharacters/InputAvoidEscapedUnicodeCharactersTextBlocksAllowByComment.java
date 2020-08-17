//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

/* Config:
 *
 * allowEscapesForControlCharacters = false
 * allowByTailComment = true
 * allowIfAllCharactersEscaped = false
 * allowNonPrintableEscapes = false
 */
public class InputAvoidEscapedUnicodeCharactersTextBlocksAllowByComment {
/** Note that "violation" comments cannot be on the same line for this config */
    public void multiplyString1() {
        // violation
        String unitAbbrev2 = "asd\u03bcsasd";
        // violation
        String unitAbbrev3 = "aBc\u03bcssdf\u03bc";
        // violation
        String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
        String unitAbbrev5 = "\u03bcs"; // Greek letter mu, "s" ok
        // violation
        String allCharactersEscaped = "\u03bc\u03bc";
    }

    public void multiplyString2() {
        // violation
        String unitAbbrev2 = """
                asd\u03bcsasd""";
        // violation
        String unitAbbrev3 = """
                aBc\u03bcssdf\u03bc""";
        // violation
        String unitAbbrev4 = """
                \u03bcaBc\u03bcssdf\u03bc""";
        String unitAbbrev5 = """
                \u03bcs"""; // Greek letter mu, "s" ok
        // violation
        String allCharactersEscaped = """
                \u03bc\u03bc""";
    }
}
