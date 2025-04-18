/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class InputUnusedLocalMethodOnlyOnPrivate {
    public void _public() {
    }

    protected void _protected() {
    }

    void _packageProtected() {
    }

    private void _private() { // violation, "Unused local method '_private'"
    }

    public static void _publicStatic() {
    }

    protected static void _protectedStatic() {
    }

    static void _packageProtectedStatic() {
    }

    private static void _privateStatic() { // violation, "Unused local method '_privateStatic'"
    }
}
