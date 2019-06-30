package com.puppycrawl.tools.checkstyle.checks.coding.equalshashcode;

public class InputEqualsHashCode {
    public boolean notEquals() {
        return true;
    }

    public boolean equals() {
        return false;
    }

    public boolean equals(Object o1) {
        return false;
    }

    private boolean equals(Object o1, Object o2) {
        return false;
    }

    private boolean equals(String s) {
        return false;
    }

    protected int notHashCode() {
        return 1;
    }

    public int hashCode() {
        return 1;
    }

    public int hashCode(Object o1) {
        return 1;
    }

    private int hashCode(Object o1, Object o2) {
        return 1;
    }
}
