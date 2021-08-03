/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public final class InputRequireThisBraceAlone { // ok
    protected void test() throws Exception {
        {
            boolean var1 = false;

            var1 = true;
        }

        {
            boolean var2 = false;

            var2 = true;
        }
    }
}
