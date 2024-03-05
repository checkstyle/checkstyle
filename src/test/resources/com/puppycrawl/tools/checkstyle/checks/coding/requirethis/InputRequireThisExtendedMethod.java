/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.logging.Logger;

public class InputRequireThisExtendedMethod
{
    public class Check {
        private Logger log1 = Logger.getLogger(getClass().getName());
    }

    String ELIST;
    String method(Object a) {
        String ELIST = "abc";
        ELIST = this.method(new Check() {
        });
        return "";
    }

    int EXPR;

    String method(int EXPR) {
        EXPR += 12; // violation 'Reference to instance variable 'EXPR' needs "this.".'
        return "someString";
    }
}
