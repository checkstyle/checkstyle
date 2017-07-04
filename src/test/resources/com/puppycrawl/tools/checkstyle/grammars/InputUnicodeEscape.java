package com.puppycrawl.tools.checkstyle.grammars;

/**
 * Input for unicode escapes.
 */
public class InputUnicodeEscape
{
    char a = '\u005cr';
    char b = '\u005cn';
    char c = '\u005ct';
    char d = '\uuuu005cn';
    char e = '\u005c\u005c';
    char f = '\u005c'';
    char g = '"';
    String h = "\u005c"";
    String i = "'";
    char j = '\"';
    String k = "\'";
    char l = '\u005C'';
    char m = '\uABCD';
    char n = '\u00AB';
    char o = '\u005B';
    char p = '\uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu005cr'; // Tests the lookahead
    char q = '\uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu005D';
    char r = '\u005c\u0027';
    char s = '\uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu005c\uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu0027';
    
    char wtf1 = '\u005c\u005c'; // This is a legal backslash
    String wtf2 = "\\u005c";    // = "\u005c", with a single backslash, and != a backslash!
                                // There is an ambiguity in the grammar, the interpretation is done as "\\" + "u005c"
    //char wtf3 = '\\u005c';    // This is therefore, illegal

    //char z = '\u005cu005c'; /* This is illegal */
    
    String sa = "\u0078\u006b\u0020\u005c\u0022\u003f\u0020\u19e8\u19f2\u19ec";
    String sb = "ihcp gyqnaznr \u2d21\u2d07\u2d0a\u2d02\u2d23\u2d27";
    String sc = "\u3009\u3007\u3017\u3032 ]*+f?)).[. xhc";
    String sd = "\u1f073\u1f08a\u1f09d\u1f09a nfllv \u03ac\udd762\u029c";
    String se = "\u0721\ue723\ue76eM \u2ffc\u2ff1 \u123e1 tzouw \ufadaZ";
    String sf = "abc\uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu005c\u0022def";
}
