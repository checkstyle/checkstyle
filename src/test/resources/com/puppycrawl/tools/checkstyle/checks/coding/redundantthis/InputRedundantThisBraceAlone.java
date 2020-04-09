package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public final class InputRedundantThisBraceAlone {
    private boolean var1;

    protected void test() throws Exception {
        {

            boolean var1 = false;
            var1 = true;
            this.var1 = true; // no violation
        }

        {
            boolean var2 = false;
            var2 = true;
            this.var1 = false; // violation
        }

    }
}
