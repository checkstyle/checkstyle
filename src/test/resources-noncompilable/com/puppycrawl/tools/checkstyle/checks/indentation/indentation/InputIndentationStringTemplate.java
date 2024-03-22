//non-compiled with javac: Compilable with Java21 //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;//indent:0 exp:0
//indent:0 exp:0
import java.util.function.Supplier; //indent:0 exp:0
//indent:0 exp:0
//indent:0 exp:0
/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 * basicOffset = 2                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 */                                                                         //indent:1 exp:1

public class InputIndentationStringTemplate {                            //indent:0 exp:0
    int x = 2;                                                    //indent:4 exp:2 warn
    final String s =                                              //indent:4 exp:2 warn
            STR."x\{ sp(() -> {                                   //indent:12 exp:12
                return STR."x\{ sp(() -> {                       //indent:16 exp:14 warn
                    return STR."x\{ x }x" + "{" + "}}}";          //indent:20 exp:18 warn
                }) }x";                                            //indent:16 exp:16
            })}x";                                                 //indent:12 exp:12
    private static String sp(Supplier<String> y) {                    //indent:4 exp:2 warn
        return y.get();                                              //indent:8 exp:4 warn
    }                                                               //indent:4 exp:2 warn
}//indent:0 exp:0
