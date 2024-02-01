/*
EqualsHashCode


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

public class InputEqualsHashCode { 
    public boolean notEquals() { // ok
        return true;
    }

    public boolean equals() { // ok
        return false;
    }

    public boolean equals(Object o1) { // ok
        return false;
    }

    private boolean equals(Object o1, Object o2) {
        return false;
    }

    private boolean equals(String s) { // ok
        return false;
    }

    protected int notHashCode() { // ok
        return 1;
    }

    public int hashCode() { // ok
        return 1;
    }

    public int hashCode(Object o1) { // ok
        return 1;
    }

    private int hashCode(Object o1, Object o2) { // ok
        return 1;
    }
}
