package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public class InputRedundantThisReceiver {
    public void foo4(InputRedundantThisReceiver this) {}

    private class Inner {
        public Inner(InputRedundantThisReceiver InputRedundantThisReceiver.this) {}
    }

    public Object clone(){
        return this;
    }
}
