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

        // Case 9: DOT-wrapped nested call (EXPR -> DOT -> METHOD_CALL) //indent:8 exp:8
        sb.append(line                                                  //indent:8 exp:8
            .substring(fName.length()                                   //indent:12 exp:12
                + IGNORED_FILE_NAME.length()));                         //indent:16 exp:16

        // Case 10: single-line DOT call as arg with multi-line sibling //indent:8 exp:8
        firstMethodTwo(                                                 //indent:8 exp:8
            line.trim(),                                                //indent:12 exp:12
            secondMethod(                                               //indent:12 exp:12
                "nested"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 11: outer call with DOT-wrapped inner, chained appends  //indent:8 exp:8
        sb.append("prefix")                                             //indent:8 exp:8
            .append(line                                                //indent:12 exp:12
            .substring(0, 5));                                          //indent:12 exp:12

        // Case 12: deeply nested DOT chain                             //indent:8 exp:8
        sb.append(line                                                  //indent:8 exp:8
            .trim()                                                     //indent:12 exp:12
            .substring(0));                                             //indent:12 exp:12

        // Case 13: outer call with correct continuation args           //indent:8 exp:8
        firstMethodThree(                                               //indent:8 exp:8
            "arg1",                                                     //indent:12 exp:12
            secondMethod("a"),                                          //indent:12 exp:12
            secondMethod(                                               //indent:12 exp:12
                "nested"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 14: bare DOT field access alongside multi-line nested   //indent:8 exp:8
        firstMethodTwo(                                                 //indent:8 exp:8
            obj.field,                                                  //indent:12 exp:12
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 15: multi-level DOT field chain alongside multi-line    //indent:8 exp:8
        firstMethodTwo(                                                 //indent:8 exp:8
            obj.inner.name,                                             //indent:12 exp:12
            secondMethod(                                               //indent:12 exp:12
                "string"                                                //indent:16 exp:16
            )                                                           //indent:12 exp:12
        );                                                              //indent:8 exp:8

        // Case 16: multi-level DOT chain ending in METHOD_CALL         //indent:8 exp:8
        firstMethodTwo(                                                 //indent:8 exp:8
            obj.inner.get(),                                            //indent:12 exp:12
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

    static String line = "test";                                        //indent:4 exp:4
    static String fName = "sub";                                        //indent:4 exp:4
    static String IGNORED_FILE_NAME = "test";                           //indent:4 exp:4
    static StringBuilder sb = new StringBuilder();                      //indent:4 exp:4

    static class InnerObj {                                             //indent:4 exp:4
        String name = "n";                                              //indent:8 exp:8
        String get() {                                                  //indent:8 exp:8
            return name;                                                //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    static class Obj {                                                  //indent:4 exp:4
        String field = "f";                                             //indent:8 exp:8
        InnerObj inner = new InnerObj();                                //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    static Obj obj = new Obj();                                         //indent:4 exp:4

}                                                                       //indent:0 exp:0
