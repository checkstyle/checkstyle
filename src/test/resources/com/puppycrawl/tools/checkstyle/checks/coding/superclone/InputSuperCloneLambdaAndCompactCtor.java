/*
SuperClone

*/

package com.puppycrawl.tools.checkstyle.checks.coding.superclone;

public class InputSuperCloneLambdaAndCompactCtor {

    private final Runnable fieldLambda = () -> {
        try {
            super.clone();
        }
        catch (CloneNotSupportedException e) {
        }
    };

    private final CloneSupplier fieldRef = super::clone;

    public record MyRecord(int x) {
        public MyRecord {
            final Runnable r = () -> {
                try {
                    super.clone();
                }
                catch (CloneNotSupportedException e) {
                }
            };
        }
    }

    @FunctionalInterface
    interface CloneSupplier {
        Object get() throws CloneNotSupportedException;
    }
}

