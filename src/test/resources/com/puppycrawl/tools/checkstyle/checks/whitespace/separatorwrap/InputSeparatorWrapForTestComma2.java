/*
SeparatorWrap
option = nl
tokens = COMMA

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForTestComma2 {

    public void badCase() {
        // violation below '',' should be on a new line.'
        final String[] ENUM = { "SCHEDULED", "PROGRESS",
                "SUSPENDED", "COMPLETED", "DISCONTINUED" };
    }

    public void goodCase() {
        final String[] ENUM = { "SCHEDULED", "PROGRESS" // OK
                , "SUSPENDED", "COMPLETED", "DISCONTINUED" };
    }
}
