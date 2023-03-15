package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0


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
 */                                                                           //indent:1 exp:1
public class InputIndentationSynchronizedStatement { //indent:0 exp:0
    private static InputIndentationSynchronizedStatement instance;//indent:4 exp:4
    public static InputIndentationSynchronizedStatement getInstance() {//indent:4 exp:4
        if (instance == null) //indent:8 exp:8
            synchronized (InputIndentationSynchronizedStatement.class) {//indent:12 exp:12
                if (instance == null)//indent:16 exp:16
                    instance = new InputIndentationSynchronizedStatement();//indent:20 exp:20
            }//indent:12 exp:12
        synchronized (new Object()) {//indent:8 exp:8
instance = instance;//indent:0 exp:12 warn
        }//indent:8 exp:8
        synchronized//indent:8 exp:8
            (new Object()) {}//indent:12 exp:8 warn
        return instance;//indent:8 exp:8
    }//indent:4 exp:4
} //indent:0 exp:0
