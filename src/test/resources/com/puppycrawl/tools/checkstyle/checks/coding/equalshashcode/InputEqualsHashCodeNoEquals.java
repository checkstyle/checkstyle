/*
EqualsHashCode


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

public class InputEqualsHashCodeNoEquals { // ok
    public int hashCode() { // violation
        return 1;
    }
}
