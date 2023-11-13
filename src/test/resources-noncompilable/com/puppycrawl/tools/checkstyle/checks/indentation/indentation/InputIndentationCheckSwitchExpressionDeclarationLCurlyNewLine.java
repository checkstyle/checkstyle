//non-compiled with javac: Compilable with Java14                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0

/* Config:                                                                          //indent:0 exp:0
 *                                                                                  //indent:1 exp:1
 * basicOffset = 4                                                                  //indent:1 exp:1
 * braceAdjustment = 0                                                              //indent:1 exp:1
 * caseIndent = 4                                                                   //indent:1 exp:1
 * throwsIndent = 4                                                                 //indent:1 exp:1
 * arrayInitIndent = 4                                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                                      //indent:1 exp:1
 * forceStrictCondition = false                                                     //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
public class InputIndentationCheckSwitchExpressionDeclarationLCurlyNewLine {     //indent:0 exp:0

    public void validDeclare() {                                 //indent:4 exp:4
        String requestId = switch ("in")                                          //indent:8 exp:8
        {                                                                         //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
            default -> "also correct";                                            //indent:12 exp:12
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    public void validAssign(String result) {                      //indent:4 exp:4
        result = switch ("in")                                                    //indent:8 exp:8
        {                                                                         //indent:8 exp:8
            case "correct" -> "true";                                             //indent:12 exp:12
            default -> "also correct";                                            //indent:12 exp:12
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    public void invalidAssignLeftCurlyWithLesserIndent(String result) {      //indent:4 exp:4
        result = switch ("in")                                               //indent:8 exp:8
    {                                                                        //indent:4 exp:8 warn
            case "correct" -> "true";                                        //indent:12 exp:12
            case "incorrect" -> "true";                                      //indent:12 exp:12
            default -> "also incorrect";                                     //indent:12 exp:12
        };                                                                   //indent:8 exp:8
    }                                                                        //indent:4 exp:4
    public void invalidDeclareLeftCurlyWithLesserIndent() {                   //indent:4 exp:4
        String result = switch ("in")                                         //indent:8 exp:8
    {                                                                         //indent:4 exp:8 warn
            case "correct" -> "true";                                         //indent:12 exp:12
            case "incorrect" -> "true";                                       //indent:12 exp:12
            default -> "also incorrect";                                      //indent:12 exp:12
        };                                                                    //indent:8 exp:8
    }                                                                         //indent:4 exp:4
    public void invalidAssignLeftCurlyWithBiggerIndent(String result) {      //indent:4 exp:4
        result = switch ("in")                                               //indent:8 exp:8
            {                                                                //indent:12 exp:8 warn
            case "correct" -> "true";                                        //indent:12 exp:12
            case "incorrect" -> "true";                                      //indent:12 exp:12
            default -> "also incorrect";                                     //indent:12 exp:12
        };                                                                   //indent:8 exp:8
    }                                                                        //indent:4 exp:4
    public void invalidDeclareLeftCurlyWithBiggerIndent() {                  //indent:4 exp:4
        String result = switch ("in")                                        //indent:8 exp:8
            {                                                                //indent:12 exp:8 warn
            case "correct" -> "true";                                        //indent:12 exp:12
            case "incorrect" -> "true";                                      //indent:12 exp:12
            default -> "also incorrect";                                     //indent:12 exp:12
        };                                                                   //indent:8 exp:8
    }                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0
