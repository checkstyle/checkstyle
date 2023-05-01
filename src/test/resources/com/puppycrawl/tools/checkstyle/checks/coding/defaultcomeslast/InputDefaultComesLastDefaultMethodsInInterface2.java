/*
DefaultComesLast
skipIfLastAndSharedWithCase = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.defaultcomeslast;

public interface InputDefaultComesLastDefaultMethodsInInterface2 {
    default int apply() { // ok
        return 1;
    }
}
