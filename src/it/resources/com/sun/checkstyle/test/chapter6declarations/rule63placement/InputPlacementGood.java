package com.sun.checkstyle.test.chapter6declarations.rule63placement;
// violation first line 'Header mismatch.*'

final class InputPlacementGood {

    /** Current count. */
    private int count;

    void setCount(final int newCount) {
        count = newCount;
    }

}
