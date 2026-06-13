/*
EqualsHashCode


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

public class InputEqualsHashCodeOveride {
    @Override
    public int hashCode() { // violation 'without .* of 'equals()'.'
        return 0;
    }
}
