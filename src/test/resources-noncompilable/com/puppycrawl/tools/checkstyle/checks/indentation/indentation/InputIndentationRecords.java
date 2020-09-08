//non-compiled with javac: Compilable with Java14                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0
                                                                                  //indent:82 exp:82
import java.util.List;                                                              //indent:0 exp:0
                                                                                  //indent:82 exp:82
/* Config:                                                                          //indent:0 exp:0
 *                                                                                  //indent:1 exp:1
 * tabwidth = 4
 * basicOffset = 4
 * braceAdjustment = 0
 * caseIndent = 4
 * throwsIndent = 4
 * arrayInitIndent = 4
 * lineWrappingIndentation = 4
 * forceStrictCondition = false                                                                    //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
public class InputIndentationRecords {                                              //indent:0 exp:0
    public record InnerRecord(List<String> errors) {                                //indent:4 exp:4
        public boolean isSuccess () {                                               //indent:8 exp:8
            return errors.isEmpty();                                              //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    public static class InnerClass {                                                //indent:4 exp:4
        public boolean isSuccess() {                                                //indent:8 exp:8
            return true;                                                          //indent:12 exp:12
        }                                                                           //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0
