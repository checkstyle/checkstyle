////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for detection of double checked locking
 * @author lkuehne
 **/
class InputDoubleCheckedLocking
{
    static Integer one = null;

    private static Integer getOneCorrect()
    {
        synchronized (InputDoubleCheckedLocking.class)
        {
            if (one == null)
            {
                one = new Integer(1);
            }
        }
        return one;
    }

    private static Integer getOneDCL()
    {
        if (one == null)
        {
            System.out.println("just to make the AST interesting");
            synchronized (InputDoubleCheckedLocking.class)
            {
                if (one == null)
                {
                    one = new Integer(1);
                }
            }
        }
        return one;
    }

    private static Integer getSimilarToDCL()
    {
        // different tests
        if (one == null)
        {
            synchronized (InputDoubleCheckedLocking.class)
            {
                if (one == Integer.valueOf(2))
                {
                    one = new Integer(1);
                }
            }
        }

        // no synchronization
        if (one == null)
        {
            if (one == null)
            {
                one = new Integer(1);
            }
        }

        // no outer test
        synchronized (InputDoubleCheckedLocking.class)
        {
            if (one == null)
            {
                one = new Integer(1);
            }
        }
        return one;
    }

}
