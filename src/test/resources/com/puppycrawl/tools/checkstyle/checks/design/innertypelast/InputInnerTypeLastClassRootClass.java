/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public enum InputInnerTypeLastClassRootClass {

    ALWAYS(Bits.YES), NEVER(Bits.NO);

    private interface Bits {
        public static final int YES = 1;

        public static final int NO = 4;
    }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    private final int bits;

    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    private InputInnerTypeLastClassRootClass(int bits) {
        this.bits = bits;
    }
}
