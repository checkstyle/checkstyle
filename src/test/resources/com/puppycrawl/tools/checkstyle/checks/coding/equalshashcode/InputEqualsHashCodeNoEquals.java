/*
EqualsHashCode


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

public class InputEqualsHashCodeNoEquals {
    public int hashCode() { // violation
        return 1;
    }
}
