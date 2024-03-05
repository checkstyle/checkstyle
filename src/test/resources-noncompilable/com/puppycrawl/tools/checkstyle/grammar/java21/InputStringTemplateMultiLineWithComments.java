//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

import java.util.List;

public class InputStringTemplateMultiLineWithComments {
    int x = 10;
    int y = 20;

    // most basic example, empty string template expression
    String result1 = STR."";

    String result1_1 = STR."Hello world!";

    // no content, no expression
    String result1_2 = STR."\{  }";

    // simple single value interpolation
    String result2 = STR. "x\{ x }x"; // comment

    // simple single value interpolation with a method call
    String result3 = STR. "\{ y(y("y")) }";

    // bit more complex example, multiple interpolations
    String result4 = STR. "\{ x }"  + STR. "\{ y }";

    // bit more complex example, multiple interpolations with method calls
    String result5 = STR. "\{ y(y("y")) }"  + STR. "\{ y(y("y"/*yep, comment here, too*/)) }";

    // string template concatenation
    String result6 = STR. "\{ x }"  + /*comment here*/ STR. "\{ y }";

    // concatentation in embedded expression
    String result7 = STR. "\{ x + y /*comment here too*/ }";

    // concatentation in embedded expression with method calls
    String result8 = STR./*comment here*/ "hey \{ y(y("y")) + y(y("y")) } there";

    // multiple lines
    String result9 = STR. "hey \{ // comment here
            y(y("y")) + y(y("y"))
            } there";

    // multiple lines with many expressions and comments
    String result10 = STR. "hey \{ /*****comment here*/ // comment here
            y(y("y")) + y(y("y")) /* comment here */
            } there \{ /**/ // comment here
            y(y("y")) + y(y("y")) // comment there
            + STR."x\{ x }x" // comment everywhere
            }";

    String name = "world";
    String s = STR."Hello, \{ name }!";

    private static String y(String y) {
        return "y";
    }

    // Verify proper behavior of TransType w.r.t. templated Strings
    void x(List<? extends StringTemplate.Processor<String, RuntimeException>> list) {
        list.get(0)."";
        list.get(0)."\{  }";
        list.get(0). "x\{ x }x"; // comments in here
        list.get(0). ".\{ x }.\{ y }.\{ x /*comments in here, as well*/ }.\{}.\{}";
    }
}
