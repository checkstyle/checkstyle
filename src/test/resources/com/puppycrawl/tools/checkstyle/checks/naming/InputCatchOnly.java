package com.puppycrawl.tools.checkstyle.checks.naming;

/**
 * Test case for skipping over catch names.
 **/
public class InputCatchOnly {
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

    public InputCatchOnly()
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
