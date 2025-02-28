/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class UnusedLocalMethodMulti {
    public UnusedLocalMethodMulti() {
        used();
    }

    private void used() {
    }

    private void unused1() { // violation, "Unused local method 'unused1'"
    }

    private void unused2() { // violation, "Unused local method 'unused2'"
    }

    private void unused3() { // violation, "Unused local method 'unused3'"
    }
}
