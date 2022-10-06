package org.checkstyle.suppressionxpathfilter.chainedmethodcallwrap;

import static com.google.common.truth.Truth.assertWithMessage;

public class SuppressionXpathRegressionChainedMethodCallWrap {

    public void test() {
        assertWithMessage("")
                .that(1).isEqualTo(1); // warn
    }

}
