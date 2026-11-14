/*
SuperFinalize

*/

package com.puppycrawl.tools.checkstyle.checks.coding.superfinalize;

public class InputSuperFinalizeLambdaAndCompactCtor {

    @SuppressWarnings("deprecation")
    private final Runnable fieldLambda = () -> {
        try {
            super.finalize();
        }
        catch (Throwable e) {
        }
    };

    public record MyRecord(int x) {
        @SuppressWarnings("deprecation")
        public MyRecord {
            final Runnable r = () -> {
                try {
                    super.finalize();
                }
                catch (Throwable e) {
                }
            };
        }
    }
}

