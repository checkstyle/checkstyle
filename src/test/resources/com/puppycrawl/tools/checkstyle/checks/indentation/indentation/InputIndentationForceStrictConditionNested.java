package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

/**                                                                              //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:      //indent:1 exp:1
 *                                                                               //indent:1 exp:1
 * basicOffset = 4                                                               //indent:1 exp:1
 * braceAdjustment = 0                                                           //indent:1 exp:1
 * caseIndent = 4                                                                //indent:1 exp:1
 * forceStrictCondition = true                                                   //indent:1 exp:1
 * lineWrappingIndentation = 8                                                   //indent:1 exp:1
 * tabWidth = 4                                                                  //indent:1 exp:1
 * throwsIndent = 8                                                              //indent:1 exp:1
 *                                                                               //indent:1 exp:1
 */                                                                              //indent:1 exp:1
public class InputIndentationForceStrictConditionNested {                        //indent:0 exp:0

    boolean b1 = false;                                                          //indent:4 exp:4
    boolean b2 = false;                                                          //indent:4 exp:4
    boolean b3 = false;                                                          //indent:4 exp:4

    void test1(Object a, boolean b) {                                            //indent:4 exp:4
        test1(new Object(),                                                      //indent:8 exp:8
                b1                                                               //indent:16 exp:16
                        && b2);                                                  //indent:24 exp:24
    }                                                                            //indent:4 exp:4

    void test2(Object a, boolean b) {                                            //indent:4 exp:4
        test2(new Object(),                                                      //indent:8 exp:8
                b1                                                               //indent:16 exp:16
                        || b2);                                                  //indent:24 exp:24
    }                                                                            //indent:4 exp:4

    void test3(Object a, Object b) {                                             //indent:4 exp:4
        test3(new Object(),                                                      //indent:8 exp:8
              new Object());                                                     //indent:14 exp:12,16 warn
    }                                                                            //indent:4 exp:4

    void test4(Object a, boolean b) {                                            //indent:4 exp:4
        test1(new Object(),                                                      //indent:8 exp:8
                b1                                                               //indent:16 exp:16
                        && b2                                                    //indent:24 exp:24
                                && b3);                                          //indent:32 exp:32
    }                                                                            //indent:4 exp:4

}                                                                                //indent:0 exp:0
