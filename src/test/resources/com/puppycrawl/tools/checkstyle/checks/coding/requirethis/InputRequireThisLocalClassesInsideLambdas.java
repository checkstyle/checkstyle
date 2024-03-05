/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.function.Consumer;

public interface InputRequireThisLocalClassesInsideLambdas {

    private static void method(Consumer<String> consumer) {
        consumer.accept("foo");
    }

    private static void testCtorNestedInLambdaWithViolation() {
        method(s -> {
            class InnerSubscriber implements InputRequireThisLocalClassesInsideLambdas {
                int index;

                public InnerSubscriber(int index) {
                    this.index = index;
                }
            }
        });
    }
}
