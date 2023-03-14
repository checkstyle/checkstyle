package com.google.checkstyle.test.chapter2filebasic.rule233nonascii;

public class InputAvoidEscapedUnicodeCharacters {

    /*warn*/private String unitAbbrev2 = "\u03bcs";

    private String unitAbbrev3 = "\u03bcs"; //Greek letter mu ok

    private String unitAbbrev4 =
            "\u03bcs"; //Greek letter mu

    public Object fooString()
    {
        String unitAbbrev = "μs";
        /*warn*/String unitAbbrev2 = "\u03bcs";
        String unitAbbrev3 = "\u03bcs"; // Greek letter mu, "s" ok
        String fakeUnicode = "asd\tsasd";
        String fakeUnicode2 = "\\u23\\u123i\\u";
        String content = "";
        /*byte order mark ok*/return "\ufeff" + content ;
    }

    public Object fooChar()
    {
        /*warn*/char unitAbbrev2 = '\u03bc';
        char unitAbbrev3 = '\u03bc'; // Greek letter mu, "s" ok
        String content = "";
        /*byte order mark ok*/return '\ufeff' + content;
    }

    public void multiplyString()
    {
        /*warn*/String unitAbbrev2 = "asd\u03bcsasd";
        String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; /* Greek letter mu, "s"*/ //ok
        /*warn*/String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
        /*warn*/String allCharactersEscaped = "\u03bc\u03bc";
    }
}
