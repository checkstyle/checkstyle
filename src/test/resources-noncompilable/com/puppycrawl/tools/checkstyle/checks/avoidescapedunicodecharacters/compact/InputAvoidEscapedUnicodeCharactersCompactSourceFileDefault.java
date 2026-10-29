/*
AvoidEscapedUnicodeCharacters
allowByTailComment = (default)false
allowEscapesForControlCharacters = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

// non-compiled with javac: Compilable with Java25

String unitAbbrev1 = "\u03bcs"; // violation 'Unicode escape(s) usage should be avoided.'

String unitAbbrev2 = "\u03bcs"; // Greek letter mu
// violation above 'Unicode escape(s) usage should be avoided.'

char unitAbbrev3 = '\u03bc'; // violation 'Unicode escape(s) usage should be avoided.'

String allEscaped = "\u03bc\u03bc"; // violation 'Unicode escape(s) usage should be avoided.'

String controlChar = "\u0001"; // violation 'Unicode escape(s) usage should be avoided.'

String nonPrintable = "\u2002"; // violation 'Unicode escape(s) usage should be avoided.'

String valid = "plain text";

void main() {
    String local = "\u03bcs"; // violation 'Unicode escape(s) usage should be avoided.'
}

