package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

/**
 * Test case for skipping over catch names.
 **/
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

    public InputParameterNameCatchOnly()
    {
        return;
    }

    class InnerFoo
    {
        public void fooInnerMethod ()
        {
        }
    }
}
