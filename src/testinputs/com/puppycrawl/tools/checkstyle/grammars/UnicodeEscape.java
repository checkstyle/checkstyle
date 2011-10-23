package com.puppycrawl.tools.checkstyle.grammars;

/**
 * Input for unicode escapes.
 */
public class UnicodeEscape
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
    
    char wtf1 = '\u005c\u005c'; // This is a legal backslash
    String wtf2 = "\\u005c";    // = "\u005c", with a single backslash, and != a backslash!
                                // There is an ambiguity in the grammar, the interpretation is done as "\\" + "u005c"
    //char wtf3 = '\\u005c';    // This is therefore, illegal

    //char z = '\u005cu005c'; /* This is illegal */
}
