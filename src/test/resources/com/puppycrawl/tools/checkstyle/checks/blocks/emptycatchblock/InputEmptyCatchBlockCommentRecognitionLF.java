/*
EmptyCatchBlock
exceptionVariableName = expected|ignore|myException
commentFormat = This is expected


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptycatchblock;

import java.io.IOException;

public class InputEmptyCatchBlockCommentRecognitionLF {
    private void some() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation 'Empty catch block'
            /* ololo
             * blalba
             */
        }
    }
    private void some1() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation 'Empty catch block'
            /* lalala
             * This is expected
             */
        }
    }
    private void some2() {
        try {
            throw new IOException();
        } catch (IOException e) {
            /*
             * This is expected
             * lalala
             */
        }
    }
    private void some3() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation 'Empty catch block'
            // some comment
            //This is expected
        }
    }
    private void some4() {
        try {
            throw new IOException();
        } catch (IOException e) {
            //This is expected
            // some comment
        }
    }
    private void some5() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation 'Empty catch block'
            /* some comment */
            //This is expected
        }
    }

    private void emptyMultilineComment() {
        try {
            throw new IOException();
        } catch (IOException e) { // violation 'Empty catch block'
            /*
*/
        }
    }
}
