package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

/**                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:  //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 * arrayInitIndent = 4                                                       //indent:1 exp:1
 * basicOffset = 4                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                       //indent:1 exp:1
 * caseIndent = 4                                                            //indent:1 exp:1
 * forceStrictCondition = false                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * throwsIndent = 4                                                          //indent:1 exp:1
 */                                                                          //indent:1 exp:1
public class InputIndentationMethodReturningArray {                          //indent:0 exp:0
    String[] correctlyIndented() {                                           //indent:4 exp:4
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

    java.lang.String[] qualifiedCorrectlyIndented() {                        //indent:4 exp:4
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

String[] notIndented() {return null;}                                        //indent:0 exp:4 warn

        int[][] overIndented() {                                             //indent:8 exp:4 warn
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

  java.util.List<String>[] underIndented() {                                 //indent:2 exp:4 warn
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

  java.lang.String[] qualifiedArray() {                                      //indent:2 exp:4 warn
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

  java.lang.String qualifiedType() {                                         //indent:2 exp:4 warn
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

      java.util.Map.Entry<String, String> nestedQualifiedType() {            //indent:6 exp:4 warn
        return null;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0
