package com.sun.checkstyle.test.chapter6declarations.rule63placement;
// violation first line 'Header mismatch.*'

final class InputPlacementBad {

    /** Current count. */
    private int count;

    void setCount(final int count) { // violation ''count' hides a field'
        this.count = count;
    }

}
