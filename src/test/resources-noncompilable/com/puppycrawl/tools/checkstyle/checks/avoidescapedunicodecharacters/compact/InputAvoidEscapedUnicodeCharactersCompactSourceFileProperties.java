/*
AvoidEscapedUnicodeCharacters
allowByTailComment = true
allowEscapesForControlCharacters = true
allowIfAllCharactersEscaped = true
allowNonPrintableEscapes = true


*/

// non-compiled with javac: Compilable with Java25

String withComment = "\u03bcs"; // Greek letter mu

String allEscaped = "\u03bc\u03bc";

char allEscapedChar = '\u03bc';

String controlChar = "\u0001";

String nonPrintable = "\u2002";

String notAllowed = "prefix \u03bcs suffix";
// violation above 'Unicode escape(s) usage should be avoided.'

void main() {
    String localNotAllowed = "prefix \u03bcs";
    // violation above 'Unicode escape(s) usage should be avoided.'
}
