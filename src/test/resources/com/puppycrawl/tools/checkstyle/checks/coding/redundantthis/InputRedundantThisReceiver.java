/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisReceiver {
    public void foo4(InputRedundantThisReceiver this) {}

    private class Inner {
        public Inner(InputRedundantThisReceiver InputRedundantThisReceiver.this) {}
    }

    public Object clone(){
        return this;
    }
}
