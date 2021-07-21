//non-compiled with javac: Compilable with Java14                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0
                                                                                  //indent:82 exp:82
/* Config:                                                                          //indent:0 exp:0
 *                                                                                  //indent:1 exp:1
 * basicOffset = 4                                                                  //indent:1 exp:1
 * braceAdjustment = 0                                                              //indent:1 exp:1
 * caseIndent = 4                                                                   //indent:1 exp:1
 * throwsIndent = 4                                                                 //indent:1 exp:1
 * arrayInitIndent = 4                                                              //indent:1 exp:1
 * lineWrappingIndentation = 8                                                      //indent:1 exp:1
 * forceStrictCondition = false                                                     //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
public class InputIndentationCheckSwitchExpressionDeclarationCorrect {              //indent:0 exp:0
                                                                                  //indent:82 exp:82
    public static void main(String... args) {                                       //indent:4 exp:4
        if (args.length < 2) {                                                      //indent:8 exp:8
            return;                                                               //indent:12 exp:12
        }                                                                           //indent:8 exp:8
        String requestId = switch (args[0]) {                                       //indent:8 exp:8
            case "CREATE" -> "RANDOM";                                            //indent:12 exp:12
            case "UPDATE" -> args[1];                                             //indent:12 exp:12
            default -> "DEFAULT";                                                 //indent:12 exp:12
        };                                                                          //indent:8 exp:8
        System.out.println(requestId);                                              //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0
                                                                                  //indent:82 exp:82
