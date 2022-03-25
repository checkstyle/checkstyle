/*
ChainedMethodCallWrap
identifierPattern = ^assert.*$
maxCallsInMultiLine = 2
maxCallsInSingleLine = 2

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.chainedmethodcallwrap;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import com.google.common.truth.Truth;

public class InputChainedMethodCallWrapMaxCallsInSingleLine {

    void method() {
        assertWithMessage("test").that(1).isEqualTo(1);
                           // violation above '3 method calls on single line, max allowed is 2'
        assertWithMessage("test")
                .that(1).isNotEqualTo(2); // ok
        Truth.assertWithMessage("test").that(1).isNotEqualTo(2);
                                                       // ^---- ok, specified pattern is different
        new InputChainedMethodCallWrapMaxCallsInSingleLine().method();

        assertThat(true).isNoneOf(true, false, true); // ok

        assertWithMessage("").that(1).isEqualTo // ok, check analyzes on
                (2);                                         // basis of METHOD_CALL token
    }

    void anotherMethod() {
        assertWithMessage("SomeShortString".
                substring("".length()).toLowerCase().substring(4)).that // ok
                (1).isEqualTo(2);
    }

}
