/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = (default)4
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4


*/
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

public class InputIndentationLambdaCastAsInvocationTarget {                     //indent:0 exp:0
    void method1() {                                                            //indent:4 exp:4
        ((Runnable) (() -> {                                                    //indent:8 exp:8
                        // violation below ''block' child has incorrect indentation level 24, expected level should be one of the following: 12, 20.'
                        int x = 1; }))                                          //indent:24 exp:12,20 warn
                .run();                                                         //indent:16 exp:16
    }                                                                           //indent:4 exp:4

    void method2() {                                                            //indent:4 exp:4
        ((Runnable) (() -> {                                                    //indent:8 exp:8
            int x = 1;                                                          //indent:12 exp:12
        }))                                                                     //indent:8 exp:8
                .run();                                                         //indent:16 exp:16
    }                                                                           //indent:4 exp:4

    void method3() {                                                            //indent:4 exp:4
        ((Runnable) (() -> {                                                    //indent:8 exp:8
                        // violation below ''block' child has incorrect indentation level 24, expected level should be one of the following: 12, 20.'
                        System.out.println(this); }))                           //indent:24 exp:12,20 warn
                .run();                                                         //indent:16 exp:16
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0
