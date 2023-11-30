/*
ChainedMethodCallWrap
identifierPattern = (default)^$
maxCallsInMultiLine = (default)1
maxCallsInSingleLine = (default)1

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

import static com.google.common.truth.Truth.assertWithMessage;

public class InputChainedMethodCallWrap {

    void method() {
        assertWithMessage("").that(1).isEqualTo(1);
        assertWithMessage("")
                .that(1).isEqualTo(1);
    }

}
