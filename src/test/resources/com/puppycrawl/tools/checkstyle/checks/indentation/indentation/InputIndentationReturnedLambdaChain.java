/* Config:                                                                           //indent:0 exp:0
 * basicOffset = 4                                                                   //indent:1 exp:1
 * braceAdjustment = 0                                                               //indent:1 exp:1
 * caseIndent = 4                                                                    //indent:1 exp:1
 * forceStrictCondition = false                                                      //indent:1 exp:1
 * lineWrappingIndentation = 4                                                       //indent:1 exp:1
 * tabWidth = 4                                                                      //indent:1 exp:1
 * throwsIndent = 4                                                                  //indent:1 exp:1
 * arrayInitIndent = 4                                                               //indent:1 exp:1
 */                                                                                  //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;              //indent:0 exp:0

import java.util.function.Function;                                                  //indent:0 exp:0

class InputIndentationReturnedLambdaChain {                                          //indent:0 exp:0
    boolean underIndented() {                                                        //indent:4 exp:4
        return handle(value -> {                                                     //indent:8 exp:8
            if (value.isEmpty()) {                                                   //indent:12 exp:12
                return true;                                                         //indent:16 exp:16
            }                                                                        //indent:12 exp:12
            return false;                                                            //indent:12 exp:12
        }, failure -> false)                                                         //indent:8 exp:8
        .apply("value");                                                             //indent:8 exp:12 warn
    }                                                                                //indent:4 exp:4

    boolean aligned() {                                                              //indent:4 exp:4
        return handle(value -> {                                                     //indent:8 exp:8
            if (value.isEmpty()) {                                                   //indent:12 exp:12
                return true;                                                         //indent:16 exp:16
            }                                                                        //indent:12 exp:12
            return false;                                                            //indent:12 exp:12
        }, failure -> false)                                                         //indent:8 exp:8
            .apply("value");                                                         //indent:12 exp:12
    }                                                                                //indent:4 exp:4

    Function<String, Boolean> handle(Function<String, Boolean> action,               //indent:4 exp:4
            Function<Exception, Boolean> failure) {                                  //indent:12 exp:12
        return action;                                                               //indent:8 exp:8
    }                                                                                //indent:4 exp:4
}                                                                                    //indent:0 exp:0
