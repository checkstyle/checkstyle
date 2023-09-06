/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersTextBlocks {

    public void multiplyString1() {
        String unitAbbrev2 = "asd\u03bcsasd"; // violation
        String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; // violation
        String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc"; // violation
        String allCharactersEscaped = "\u03bc\u03bc"; // violation
    }

    public void multiplyString2() {
        String unitAbbrev2 = """
            asd\u03bcsasd"""; // violation above
        String unitAbbrev3 = """
            aBc\u03bcssdf\u03bc"""; // violation above
        String unitAbbrev4 = """
            \u03bcaBc\u03bcssdf\u03bc"""; // violation above
        String allCharactersEscaped = """
            \u03bc\u03bc"""; // violation above
    }
}

