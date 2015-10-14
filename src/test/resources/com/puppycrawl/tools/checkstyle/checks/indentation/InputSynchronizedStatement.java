package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0


/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1                                                                   //indent:1 exp:1
public class InputSynchronizedStatement { //indent:0 exp:0
    private static InputSynchronizedStatement instance;//indent:4 exp:4
    public static InputSynchronizedStatement getInstance() {//indent:4 exp:4
        if (instance == null) //indent:8 exp:8
            synchronized (InputSynchronizedStatement.class) {//indent:12 exp:12
                if (instance == null)//indent:16 exp:16
                    instance = new InputSynchronizedStatement();//indent:20 exp:20
            }//indent:12 exp:12
        return instance;//indent:8 exp:8
    }//indent:4 exp:4
} //indent:0 exp:0
