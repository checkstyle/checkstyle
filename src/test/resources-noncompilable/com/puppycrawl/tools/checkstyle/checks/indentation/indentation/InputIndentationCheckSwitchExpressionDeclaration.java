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
public class InputIndentationCheckSwitchExpressionDeclaration {                     //indent:0 exp:0
                                                                                  //indent:82 exp:82
    public void validDeclare() {                                                    //indent:4 exp:4
        String requestId = switch ("in") {                                          //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
            default -> "also correct";                                            //indent:12 exp:12
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    public void validAssign(String result) {                                        //indent:4 exp:4
        result = switch ("in") {                                                    //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
            default -> "also correct";                                            //indent:12 exp:12
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    public void invalidDeclareWithBiggerIndent() {                                  //indent:4 exp:4
        String requestId = switch ("in") {                                          //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
                case "incorrect" -> "true";                                  //indent:16 exp:12 warn
                default -> "also incorrect";                                 //indent:16 exp:12 warn
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    public void invalidAssignWithBiggerIndent(String result) {                      //indent:4 exp:4
        result = switch ("in") {                                                    //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
                case "incorrect" -> "true";                                  //indent:16 exp:12 warn
                default -> "also incorrect";                                 //indent:16 exp:12 warn
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    public void invalidDeclareWithLesserIndent() {                                  //indent:4 exp:4
        String requestId = switch ("in") {                                          //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
        case "incorrect" -> "true";                                           //indent:8 exp:12 warn
        default -> "also incorrect";                                          //indent:8 exp:12 warn
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    public void invalidAssignWithLesserIndent(String result) {                      //indent:4 exp:4
        result = switch ("in") {                                                    //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
        case "incorrect" -> "true";                                           //indent:8 exp:12 warn
        default -> "also incorrect";                                          //indent:8 exp:12 warn
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0
                                                                                  //indent:82 exp:82
