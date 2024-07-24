package com.google.checkstyle.test.chapter2filebasic.rule233nonascii;

/** Some javadoc. */
public class InputNonAsciiCharacters {

  private String unitAbbrev2 = "\u03bcs";
  // violation above 'Unicode escape(s) usage should be avoided.'

  private String unitAbbrev3 = "\u03bcs"; // Greek letter mu ok

  private String unitAbbrev4 = "\u03bcs"; // Greek letter mu

  /** Some javadoc. */
  public Object fooString() {
    String unitAbbrev = "μs";
    String unitAbbrev2 = "\u03bcs";
    // violation above 'Unicode escape(s) usage should be avoided.'
    final String r4 = "\u1111sdfsd\444";
    // violation above 'Unicode escape(s) usage should be avoided.'
    String unitAbbrev3 = "\u03bcs"; // Greek letter mu, "s" ok
    String fakeUnicode = "asd\tsasd";
    String fakeUnicode2 = "\\u23\\u123i\\u";
    String content = "";
    /*byte order mark ok*/ return "\ufeff" + content;
  }

  /** Some javadoc. */
  public Object fooChar() {
    char unitAbbrev2 = '\u03bc';
    // violation above 'Unicode escape(s) usage should be avoided.'
    char unitAbbrev3 = '\u03bc'; // Greek letter mu, "s" ok
    String content = "";
    /*byte order mark ok*/ return '\ufeff' + content;
  }

  /** Some javadoc. */
  public void multiplyString() {
    String unitAbbrev2 = "asd\u03bcsasd";
    // violation above 'Unicode escape(s) usage should be avoided.'
    String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; /* Greek letter mu, "s"*/ // ok
    String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
    // violation above 'Unicode escape(s) usage should be avoided.'
    String allCharactersEscaped = "\u03bc\u03bc";
    // violation above 'Unicode escape(s) usage should be avoided.'
  }
}
