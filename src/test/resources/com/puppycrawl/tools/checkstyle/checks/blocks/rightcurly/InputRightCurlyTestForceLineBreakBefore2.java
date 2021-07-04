/*
RightCurly
option = (default)same
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestForceLineBreakBefore2
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

        synchronized (this) { do { x = 2; } while (x == 2); } // ok

        synchronized (this) {
            do {} while (x == 2); // ok
        }

        for (int k = 0; k < 1; k++) { String innerBlockVariable = ""; } // ok

        for (int k = 0; k < 1; k++) {} // ok
                return a;
    }

    static { int x = 1; } // ok

    void method2()
    {
        boolean flag = false;
        if (flag) { String.valueOf("foo"); }
    }
}

class Absent_CustomFieldSerializerTestLineBreakBefore2 {

    public static void serialize() {}
}

class Absent_CustomFieldSerializer10TestLineBreakBefore2
{
    public void Absent_CustomFieldSerializer10() {}
}

class EmptyClassTestLineBreakBefore2 {}

interface EmptyInterfaceTestLineBreakBefore2 {}
