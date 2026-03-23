/*
RightCurlyAloneOrEmpty
tokens = CLASS_DEF, ANNOTATION_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptyTypeDeclarations {

    // violation 2 lines below ''}' at column 5 should be alone on a line'
    class Inner {
    } // comment

    class InnerViolation {}

    class InnerWithComment {
        // comment
    }

    // violation below ''}' at column 37 should be alone on a line'
    class InnerClosingWithComment { } // comment

    // violation 3 lines below '}' at column 5 should be alone on a line'
    enum Status {
        A, B
    } /* Comment here */

    // violation below ''}' at column 33 should be alone on a line'
    enum StatusViolation { A, B }

    enum StatusWithComment {
        // comment
        A, B
    }

    interface Readable {
    }

    // violation below ''}' at column 49 should be alone on a line'
    interface ReadableViolation {/** javadoc **/}

    interface ReadableWithComment {
        // comment
    }

    @interface Note {
    }

    @interface NoteViolation {

    }

    // violation 3 lines below ''}' at column 5 should be alone on a line'
    @interface NoteWithComment {
        // comment
    } record Point(int x, int y) {
    }

    // violation below ''}' at column 57 should be alone on a line'
    record PointViolation(int x, int y) { /* comment */ }

    record PointWithComment(int x, int y) {
        // comment
    }

    class Annotated {
    }
    /* Block Comment */

    class AnnotatedViolation { }

    class AnnotatedWithComment {
        // comment
    }

    class A
    {}
}
