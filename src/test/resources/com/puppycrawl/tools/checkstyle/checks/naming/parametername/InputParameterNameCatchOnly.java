package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

/* Config:
 *
 * format = "^NO_WAY_MATEY$"
 */

public class InputParameterNameCatchOnly {
    int foo() {
        if (System.currentTimeMillis() > 1000)
            return 1;

        int test = 0;

        try
        {
            return 1;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public InputParameterNameCatchOnly() // ok
    {
        return;
    }

    class InnerFoo
    {
        public void fooInnerMethod () // ok
        {
        }
    }
}
