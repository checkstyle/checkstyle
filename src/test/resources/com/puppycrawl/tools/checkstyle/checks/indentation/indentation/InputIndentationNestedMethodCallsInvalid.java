package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

public class InputIndentationNestedMethodCallsInvalid {                       //indent:0 exp:0

    public static void main(String[] args) {                                  //indent:4 exp:4

        // invalid: non-nested arg at wrong indent                            //indent:8 exp:8
        firstMethod(                                                          //indent:8 exp:8
            "string"                                                          //indent:12 exp:12
        );                                                                    //indent:8 exp:8

        // invalid: non-nested arg at wrong indent should be caught           //indent:8 exp:8
        firstMethod(                                                          //indent:8 exp:8
        "string"                                                              //indent:8 exp:12 warn
        );                                                                    //indent:8 exp:8

        // valid nested call - should NOT be flagged                          //indent:8 exp:8
        firstMethod(                                                          //indent:8 exp:8
            secondMethod(                                                     //indent:12 exp:12
                "string"                                                      //indent:16 exp:16
            )                                                                 //indent:12 exp:12
        );                                                                    //indent:8 exp:8

        // invalid: nested call arg at wrong indent - should be caught        //indent:8 exp:8
        firstMethodTwo(                                                       //indent:8 exp:8
        "literal",                                                            //indent:8 exp:12 warn
            secondMethod(                                                     //indent:12 exp:12
                "string"                                                      //indent:16 exp:16
            )                                                                 //indent:12 exp:12
        );                                                                    //indent:8 exp:8

        // wrong indent for non-nested arg when mixed with same-line nested   //indent:8 exp:8
        firstMethodTwo(secondMethod("a"),                                     //indent:8 exp:8
        "wrongIndent"                                                         //indent:8 exp:12 warn
        );                                                                    //indent:8 exp:8

        // wrong indent on literal arg alongside nested call                  //indent:8 exp:8
        firstMethodTwo(                                                       //indent:8 exp:8
        "wrongLiteral",                                                       //indent:8 exp:12 warn
            secondMethod(                                                     //indent:12 exp:12
                "string"                                                      //indent:16 exp:16
            )                                                                 //indent:12 exp:12
        );                                                                    //indent:8 exp:8

        // same-line nested call at wrong indent - must be caught             //indent:8 exp:8
        firstMethodTwo(secondMethod("a"),                                     //indent:8 exp:8
        secondMethod("b")                                                     //indent:8 exp:12 warn
        );                                                                    //indent:8 exp:8

        // same-line nested call at wrong indent alongside multi-line nested  //indent:8 exp:8
        firstMethodThree(secondMethod("a"),                                   //indent:8 exp:8
        secondMethod("b"),                                                    //indent:8 exp:12 warn
            secondMethod(                                                     //indent:12 exp:12
                "string"                                                      //indent:16 exp:16
            )                                                                 //indent:12 exp:12
        );                                                                    //indent:8 exp:8

        // invalid: DOT-wrapped literal arg at wrong indent                   //indent:8 exp:8
        firstMethodTwo(                                                       //indent:8 exp:8
        "prefix",                                                             //indent:8 exp:12 warn
            line.substring(0, 5));                                            //indent:12 exp:12

        // invalid: non-nested arg at wrong indent with DOT-wrapped sibling   //indent:8 exp:8
        firstMethodTwo(                                                       //indent:8 exp:8
            line.trim(),                                                      //indent:12 exp:12
        "wrongSecondArg");                                                    //indent:8 exp:12 warn

        // invalid: wrong indent on literal alongside same-line DOT call      //indent:8 exp:8
        firstMethodTwo(                                                       //indent:8 exp:8
        "wrong",                                                              //indent:8 exp:12 warn
            line.trim());                                                     //indent:12 exp:12

        // invalid: same-line DOT call at wrong indent with multi-line        //indent:8 exp:8
        firstMethodThree(line.trim(),                                         //indent:8 exp:8
        secondMethod("wrong"),                                                //indent:8 exp:12 warn
            secondMethod(                                                     //indent:12 exp:12
                "string"                                                      //indent:16 exp:16
            )                                                                 //indent:12 exp:12
        );                                                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    private static void firstMethod(String string) {                          //indent:4 exp:4
    }                                                                         //indent:4 exp:4

    private static void firstMethodTwo(String a, String b) {                  //indent:4 exp:4
    }                                                                         //indent:4 exp:4

    private static String secondMethod(String string) {                       //indent:4 exp:4
        return string + "2";                                                  //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    private static void firstMethodThree(String a, String b, String c) {      //indent:4 exp:4
    }                                                                         //indent:4 exp:4
    static String line = "test";                                              //indent:4 exp:4
}                                                                             //indent:0 exp:0
