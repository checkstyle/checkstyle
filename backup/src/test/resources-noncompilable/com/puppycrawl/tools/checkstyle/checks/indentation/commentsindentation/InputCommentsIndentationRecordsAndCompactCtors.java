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
                // violation '.* incorrect .* level 16, expected is 20, .* same .* as line 16.'
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
// violation '.* incorrect .* level 0, expected is 4, .* same .* as line 29.'
    }

        ///////////// violation '.* incorrect .* level 8, expected is 4, .* same .* as line 32.'
    public record myOtherOtherRecord() {

    }
     class
        WrappedClass { }
        // violation '.* incorrect .* level 8, expected is 5, .* same .* as line 40.'


     record
        WrappedRecord() { }
        // violation '.* incorrect .* level 8, expected is 5, .* same .* as line 40.'
}
