package org.checkstyle.suppressionxpathfilter.chainedmethodcallwrap;

import static com.google.common.truth.Truth.assertWithMessage;

public class SuppressionXpathRegressionChainedMethodCallWrap2 {

    void test() {
        assertWithMessage("").that(1).isEqualTo(1); // warn
    }

}
