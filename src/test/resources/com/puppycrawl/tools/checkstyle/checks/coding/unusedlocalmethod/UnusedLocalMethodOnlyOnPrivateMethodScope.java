/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

public class UnusedLocalMethodOnlyOnPrivateMethodScope {
    public void unusedPublic() {
    }
    protected void unusedProtected() {
    }
    void unusedPackageProtected() {
    }
    private void unusedPrivate() { // violation, "Unused local method 'unusedPrivate'"
    }
}
