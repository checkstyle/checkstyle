/*
EqualsHashCode


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

public class InputEqualsHashCodeNoEquals {
    public int hashCode() { // violation 'Definition of 'hashCode()' without corresponding definition of 'equals()'.'
        return 1;
    }
}
