/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.io.Serializable;

public class InputNoWhitespaceAfterTestTypecastWrapped {
    void method() {
        // violation below '')' is followed by whitespace.'
        Runnable a = (Runnable & Serializable) () -> { };
        // violation below '')' is followed by whitespace.'
        Runnable b = (Runnable
                & Serializable) () -> { };
        Runnable c = (Runnable & Serializable)() -> { };
        Runnable d = (Runnable
                & Serializable)() -> { };
    }
}
