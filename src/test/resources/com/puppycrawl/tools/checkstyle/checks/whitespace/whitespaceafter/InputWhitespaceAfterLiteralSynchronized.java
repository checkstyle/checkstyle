/*
WhitespaceAfter
tokens = LITERAL_SYNCHRONIZED


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterLiteralSynchronized {

    public void check1() {
        synchronized(this) { // violation ''synchronized' is not followed by whitespace'
        }
        try {
        }
        catch (RuntimeException e){
        }
    }

    public void check2() {
        synchronized (this) {
        }
        try {
        }
        catch (RuntimeException e){
        }
    }

    public void check3() {
        synchronized(this) { } // violation ''synchronized' is not followed by whitespace'
        synchronized (this) { }
    }
}
