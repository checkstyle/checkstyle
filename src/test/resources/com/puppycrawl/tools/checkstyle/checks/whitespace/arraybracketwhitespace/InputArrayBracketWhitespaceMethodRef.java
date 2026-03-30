/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

import java.util.function.IntSupplier;

public class InputArrayBracketWhitespaceMethodRef {

    void moreMethodRefs() {
        Integer[] objArr = new Integer[2];
        IntSupplier s = objArr[0]::hashCode;
        IntSupplier s2 = objArr[0] ::hashCode; // violation ''\]' is followed by whitespace.'

        Object[] rcs = new Object[1];
        Object resources = new Object[] {(Object) rcs[0]};
        Object[] manifests = new Object[] {rcs[0] }; // violation ''\]' is followed by whitespace.'
    }
}
