/*
ChainedMethodCallWrap
identifierPattern = ^assert.*$
maxCallsPerLine = 2
maxSingleLineCalls = 2

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

import static com.google.common.truth.Truth.assertWithMessage;

import com.google.common.truth.Truth;

public class InputChainedMethodCallWrapMaxSingleLineCalls {

    void method() {
        assertWithMessage("test").that(1).isEqualTo(1);
                           // violation above ''3' method calls on single line, max allowed is '2''
        assertWithMessage("test")
                .that(1).isNotEqualTo(2); // ok
        Truth.assertWithMessage("test").that(1).isNotEqualTo(2);
                                                       // ^---- ok, specified pattern is different
        new InputChainedMethodCallWrapMaxSingleLineCalls().method();
    }

}
