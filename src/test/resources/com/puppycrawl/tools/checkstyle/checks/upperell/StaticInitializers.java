package com.puppycrawl.tools.checkstyle.checks.upperell;

public class StaticInitializers {
    /* Boolean instantiation in a static initializer */
    static {
        Boolean x = new Boolean(true);
    }

    static {
        int a = 0;
    }

    static {

    }
}
