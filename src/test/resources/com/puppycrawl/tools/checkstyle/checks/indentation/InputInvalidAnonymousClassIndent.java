package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

import java.util.concurrent.ThreadFactory; //indent:0 exp:0

import static java.util.concurrent.Executors.newFixedThreadPool; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidAnonymousClassIndent { //indent:0 exp:0

    public InputInvalidAnonymousClassIndent() { //indent:4 exp:4
        newFixedThreadPool(1, new ThreadFactory() { //indent:8 exp:8
            public Thread newThread(Runnable runnable) { //indent:12 exp:12
                if (hashCode() == 0) { //indent:16 exp:16
                    return new Thread(); //indent:20 exp:20
                } else { //indent:16 exp:16
                    return new Thread(); //indent:20 exp:20
                }}}); //indent:16 exp:16
        return; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
