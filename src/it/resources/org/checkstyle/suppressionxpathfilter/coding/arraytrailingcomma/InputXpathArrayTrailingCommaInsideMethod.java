/*
ArrayTrailingComma
alwaysDemandTrailingComma = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.arraytrailingcomma;

public class InputXpathArrayTrailingCommaInsideMethod {

    void method() {
        int[] a = new int[] {
                1,
                2,
                3 // violation 'Array should contain trailing comma.'
        };
    }
}
