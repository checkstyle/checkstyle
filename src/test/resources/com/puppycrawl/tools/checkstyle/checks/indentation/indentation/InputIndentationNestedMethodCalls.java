package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationNestedMethodCalls {                        //indent:0 exp:0

    public InputIndentationNestedMethodCalls() {                        //indent:4 exp:4
        this(                                                           //indent:8 exp:8
            secondMethod("string")                                      //indent:12 exp:12
        );                                                              //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    public InputIndentationNestedMethodCalls(String s) {                //indent:4 exp:4
    }                                                                   //indent:4 exp:4

    public static void main(String[] args) {                            //indent:4 exp:4

        // Case 1: basic two-level nesting - the original bug           //indent:8 exp:8
        firstMethod(                                                    //indent:8 exp:8
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 2: single-line nested call (not the bug case)           //indent:8 exp:8
        firstMethod(                                                    //indent:8 exp:8
            secondMethod("string")                                      //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 3: no arguments                                         //indent:8 exp:8
        firstMethod(                                                    //indent:8 exp:8
            secondMethod()                                              //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 4: three-level nesting                                  //indent:8 exp:8
        firstMethod(                                                    //indent:8 exp:8
            secondMethod(                                               //indent:12 exp:12
                thirdMethod(                                            //indent:16 exp:16
                    "string"                                            //indent:20 exp:20
                )                                                       //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 5: literal argument alongside nested call               //indent:8 exp:8
        firstMethodTwo(                                                 //indent:8 exp:8
            "literal",                                                  //indent:12 exp:12
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 6: nested call on same line as outer                    //indent:8 exp:8
        firstMethod(secondMethod(                                       //indent:8 exp:8
            "string"                                                    //indent:12 exp:12
        ));                                                             //indent:8 exp:8

        // Case 7: mixed - one nested call on same line, one multi-line //indent:8 exp:8
        firstMethodTwo(secondMethod("a"),                               //indent:8 exp:8
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // 8: sme-line single-line nested calls with multi-line sibling //indent:8 exp:8
        firstMethodThree(secondMethod("a"), secondMethod("b"),          //indent:8 exp:8
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        firstMethodTwo(                                                 //indent:8 exp:8
            secondMethod("correct"),                                    //indent:12 exp:12
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private static void firstMethod(String string) {                    //indent:4 exp:4
    }                                                                   //indent:4 exp:4

    private static void firstMethodTwo(String a, String b) {            //indent:4 exp:4
    }                                                                   //indent:4 exp:4

    private static String secondMethod(String string) {                 //indent:4 exp:4
        return string + "2";                                            //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private static String secondMethod() {                              //indent:4 exp:4
        return "empty";                                                 //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private static String thirdMethod(String string) {                  //indent:4 exp:4
        return string + "3";                                            //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private static void firstMethodThree(String a, String b, String c) {//indent:4 exp:4
    }                                                                   //indent:4 exp:4

}                                                                       //indent:0 exp:0
