/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisAdjacent {
    private int x;
    private int y = this.x;
    // violation above 'Redundant "this", field 'x' can be accessed directly.'

    private int z;
    private boolean flag;

    public void sameStatement(int x) {
        this.y = this.x;
    }

    public void afterRequired(int y) {
        this.x = 1;
        this.y = y;
    }

    public void beforeRequired(int y) {
        this.y = y;
        this.z = 1;
    }

    public void nonSemiStatementBeforeRequired(int x) {
        if (this.flag) {
            return;
        }
        this.x = x;
    }

    public void nonSemiStatementWithRequiredBeforeRedundant(boolean flag) {
        if (this.flag) {
            return;
        }
        this.x = 1;
    }

    public void nonSemiAtEndOfBlock() {
        if (this.flag) {
            // violation above 'Redundant "this", field \'flag\' can be accessed directly.'
            return;
        }
    }

    static class SuperClass {
        int z;
    }

    public void localClassInMethod(int x) {
        class Local extends SuperClass {
            int x = 10;
            int field = this.x;
            int field3 = this.z;
            // violation above 'Redundant "this", field 'z' can be accessed directly.'
        }
        SuperClass obj = new SuperClass() {
            int x = 20;
            int field2 = this.x;
            int field3 = this.z;
            // violation above 'Redundant "this", field 'z' can be accessed directly.'
        };
    }
}
