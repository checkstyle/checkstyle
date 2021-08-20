/*
RightCurly
option = ALONE
tokens = LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, STATIC_INIT, INSTANCE_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestForceLineBreakBefore
{
    /** @see test method **/
    int foo() throws InterruptedException
    {
        int x = 1;
        int a = 2;
        while (true)
        {
            try
            {
                if (x > 0)
                {
                    break;
                } else if (x < 0) {
                    ;
                } else { break; }
                switch (a)
                {
                case 0:
                    break;
                default:
                    break;
                }
            } catch (Exception e) { break; } finally { break; }
        }

        synchronized (this) { do { x = 2; } while (x == 2); } // violation

        synchronized (this) {
            do {} while (x == 2); // violation
        }

        for (int k = 0; k < 1; k++) { String innerBlockVariable = ""; } // violation

        for (int k = 0; k < 1; k++) {} // violation
                return a;
    }

    static { int x = 1; } // violation

    void method2()
    {
        boolean flag = false;
        if (flag) { String.valueOf("foo"); }
    }
}

class Absent_CustomFieldSerializer {

    public static void serialize() {}
}

class Absent_CustomFieldSerializer10
{
    public Absent_CustomFieldSerializer10() {}
}

class EmptyClass {}

interface EmptyInterface {}
