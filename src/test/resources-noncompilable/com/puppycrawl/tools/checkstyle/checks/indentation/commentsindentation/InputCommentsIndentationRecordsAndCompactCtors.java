/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationRecordsAndCompactCtors {
    public record MyRecord(int x) {
        public void myMethod() {
            String breaks = "J"
                // violation
                    + "A"
                    // it is OK
                    + "V"
                    + "A"
                    // it is OK
                    ;
        }
    }

    public record MyOtherRecord() {
// comment
//  block
// violation
    }

        ///////////// violation
    public record myOtherOtherRecord() {

    }
     class
        WrappedClass { }
        // violation


     record
        WrappedRecord() { }
        // violation
}
