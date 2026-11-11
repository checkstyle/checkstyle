/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.io.Serializable;

public class InputNoWhitespaceAfterTestTypecastWrapped {
    void method() {
        Runnable a = (Runnable & Serializable) () -> { }; // violation '')' is followed by whitespace.'
        Runnable b = (Runnable
                & Serializable) () -> { }; // violation above '')' is followed by whitespace.'
        Runnable c = (Runnable & Serializable)() -> { };
        Runnable d = (Runnable
                & Serializable)() -> { };
    }
}
