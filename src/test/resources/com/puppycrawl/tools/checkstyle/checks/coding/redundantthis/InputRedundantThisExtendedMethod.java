/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.logging.Logger;

public class InputRedundantThisExtendedMethod // ok
{
    public class Check {
        private Logger log1 = Logger.getLogger(getClass().getName());
    }
}
