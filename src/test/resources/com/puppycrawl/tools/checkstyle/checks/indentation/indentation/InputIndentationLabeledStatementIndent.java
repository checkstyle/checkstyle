package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

/**                                                                         //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationLabeledStatementIndent {                       //indent:0 exp:0

    void forLoop() {                                                        //indent:4 exp:4
        LOOP:                                                               //indent:8 exp:8
         for (int i = 0; i < 1; i++) {                                      //indent:9 exp:8,12 warn
            break LOOP;                                                     //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void doWhileLoop() {                                                    //indent:4 exp:4
        LOOP:                                                               //indent:8 exp:8
         do {                                                               //indent:9 exp:8,12 warn
            break LOOP;                                                     //indent:12 exp:12
        } while (true);                                                     //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void block() {                                                          //indent:4 exp:4
        BLOCK:                                                              //indent:8 exp:8
         {                                                                  //indent:9 exp:8,12 warn
            break BLOCK;                                                    //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void ifStatement(boolean flag) {                                        //indent:4 exp:4
        CHECK:                                                              //indent:8 exp:8
         if (flag) {                                                        //indent:9 exp:8,12 warn
            break CHECK;                                                    //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void expression(int value) {                                            //indent:4 exp:4
        EXPR:                                                               //indent:8 exp:8
         value++;                                                           //indent:9 exp:8,12 warn
    }                                                                       //indent:4 exp:4

    void validLoop() {                                                      //indent:4 exp:4
        LOOP:                                                               //indent:8 exp:8
        while (true) {                                                      //indent:8 exp:8
            break LOOP;                                                     //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0
