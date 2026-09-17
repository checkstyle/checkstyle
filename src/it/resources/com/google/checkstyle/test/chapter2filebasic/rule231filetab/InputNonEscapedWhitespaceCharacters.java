package com.google.checkstyle.test.chapter2filebasic.rule231filetab;

/** Some javadoc. */
public class InputNonEscapedWhitespaceCharacters {

  void rawWhitespace() {
    String characterTabulationStr = "a	b";
    // 2 violations above:
    // 'Whitespace other than the ASCII space must be escaped.'
    // 'Line contains a tab character.'
    String lineTabulationStr = "ab";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String formFeedStr = "ab";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String nextLineStr = "ab";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String noBreakSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String oghamSpaceMarkStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String enQuadStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String emQuadStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String enSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String emSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String threePerEmSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String fourPerEmSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String sixPerEmSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String figureSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String punctuationSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String thinSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String hairSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String lineSeparatorStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String paragraphSeparatorStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String narrowNoBreakSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String mediumMathematicalSpaceStr = "a b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    String ideographicSpaceStr = "a　b";
    // violation above 'Whitespace other than the ASCII space must be escaped.'

    char characterTabulationChar = '	';
    // 2 violations above:
    // 'Whitespace other than the ASCII space must be escaped.'
    // 'Line contains a tab character.'
    char lineTabulationChar = '';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char formFeedChar = '';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char nextLineChar = '';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char noBreakSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char oghamSpaceMarkChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char enQuadChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char emQuadChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char enSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char emSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char threePerEmSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char fourPerEmSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char sixPerEmSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char figureSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char punctuationSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char thinSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char hairSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char lineSeparatorChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char paragraphSeparatorChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char narrowNoBreakSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char mediumMathematicalSpaceChar = ' ';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
    char ideographicSpaceChar = '　';
    // violation above 'Whitespace other than the ASCII space must be escaped.'
  }

  void escapedWhitespace() {
    String characterTabulationStr = "a\tb";
    String lineTabulationStr = "a\u000Bb";
    String formFeedStr = "a\fb";
    String nextLineStr = "a\u0085b";
    String noBreakSpaceStr = "a\u00A0b";
    String oghamSpaceMarkStr = "a\u1680b";
    String enQuadStr = "a\u2000b";
    String emQuadStr = "a\u2001b";
    String enSpaceStr = "a\u2002b";
    String emSpaceStr = "a\u2003b";
    String threePerEmSpaceStr = "a\u2004b";
    String fourPerEmSpaceStr = "a\u2005b";
    String sixPerEmSpaceStr = "a\u2006b";
    String figureSpaceStr = "a\u2007b";
    String punctuationSpaceStr = "a\u2008b";
    String thinSpaceStr = "a\u2009b";
    String hairSpaceStr = "a\u200Ab";
    String lineSeparatorStr = "a\u2028b";
    String paragraphSeparatorStr = "a\u2029b";
    String narrowNoBreakSpaceStr = "a\u202Fb";
    String mediumMathematicalSpaceStr = "a\u205Fb";
    String ideographicSpaceStr = "a\u3000b";

    char characterTabulationChar = '\t';
    char lineTabulationChar = '\u000B';
    char formFeedChar = '\f';
    char nextLineChar = '\u0085';
    char noBreakSpaceChar = '\u00A0';
    char oghamSpaceMarkChar = '\u1680';
    char enQuadChar = '\u2000';
    char emQuadChar = '\u2001';
    char enSpaceChar = '\u2002';
    char emSpaceChar = '\u2003';
    char threePerEmSpaceChar = '\u2004';
    char fourPerEmSpaceChar = '\u2005';
    char sixPerEmSpaceChar = '\u2006';
    char figureSpaceChar = '\u2007';
    char punctuationSpaceChar = '\u2008';
    char thinSpaceChar = '\u2009';
    char hairSpaceChar = '\u200A';
    char lineSeparatorChar = '\u2028';
    char paragraphSeparatorChar = '\u2029';
    char narrowNoBreakSpaceChar = '\u202F';
    char mediumMathematicalSpaceChar = '\u205F';
    char ideographicSpaceChar = '\u3000';
  }

  void rawWhitespaceInTextBlock() {
    // violation 2 lines below 'Whitespace other than the ASCII space must be escaped.'
    String block =
        """
        line one
        line two
        line　three
        """;
  }

  void escapedWhitespaceInTextBlock() {
    String block =
        """
        line\u00A0one
        line\u2003two
        line\u3000three
        """;
  }
}
