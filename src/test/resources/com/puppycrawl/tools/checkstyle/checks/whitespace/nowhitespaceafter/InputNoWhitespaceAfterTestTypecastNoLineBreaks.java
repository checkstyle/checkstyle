/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.io.Serializable;

public class InputNoWhitespaceAfterTestTypecastNoLineBreaks {
    void method() {
        // violation below '')' is followed by whitespace.'
        Runnable a = (Runnable & Serializable) () -> { };
        Runnable b = (Runnable & Serializable)() -> { };
        // violation below '')' is followed by whitespace.'
        Runnable c = (Runnable
                & Serializable) () -> { };
        Runnable d = (Runnable
                & Serializable)() -> { };
        // violation below '')' is followed by whitespace.'
        Runnable e = (Runnable
                & Serializable
                )
                () -> { };
    }
}
