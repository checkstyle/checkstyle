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

    void nestedLabel() {                                                    //indent:4 exp:4
        A:                                                                  //indent:8 exp:8
    B:                                                                      //indent:4 exp:8,12 warn
        while (true) {                                                      //indent:8 exp:8
            break A;                                                        //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void validNestedLabel() {                                               //indent:4 exp:4
        A:                                                                  //indent:8 exp:8
        B:                                                                  //indent:8 exp:8
        while (true) {                                                      //indent:8 exp:8
            break A;                                                        //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void nestedLabelLoop() {                                                //indent:4 exp:4
        A:                                                                  //indent:8 exp:8
        B:                                                                  //indent:8 exp:8
         while (true) {                                                     //indent:9 exp:8,12,16 warn
            break B;                                                        //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void switchStatement(int value) {                                       //indent:4 exp:4
        SWITCH:                                                             //indent:8 exp:8
         switch (value) {                                                   //indent:9 exp:8,12 warn
            default:                                                        //indent:12 exp:12
                break SWITCH;                                               //indent:16 exp:16
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void tryStatement() {                                                   //indent:4 exp:4
        TRY:                                                                //indent:8 exp:8
         try {                                                              //indent:9 exp:8,12 warn
            break TRY;                                                      //indent:12 exp:12
        } finally {                                                         //indent:8 exp:8
            System.out.println();                                           //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    void synchronizedStatement() {                                          //indent:4 exp:4
        SYNC:                                                               //indent:8 exp:8
         synchronized (this) {                                              //indent:9 exp:8,12 warn
            break SYNC;                                                     //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0
