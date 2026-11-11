/*
RightCurly
option = ALONE
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyLeftTestNewLine
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
                }
                else if (x < 0) {
                    ;
                }
                else
                {
                    break;
                }
                switch (a)
                {
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e)
            {
                break;
            }
            finally
            {
                break;
            }
        }

        synchronized (this)
        {
            do
            {
                x = 2;
            } while (x == 2);
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }

        // test input for bug reported by Joe Comuzzi
        if (System.currentTimeMillis() > 1000)
            return 1;
        else
            return 2;
    }

    // Test static initialiser
    static
    {
        int x = 1; // should not require any javadoc
    }



    public enum GreetingsEnum
    {
        HELLO,
        GOODBYE
    }; // violation ''}' at column 5 should be alone on a line'

    void method2()
    {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } String.CASE_INSENSITIVE_ORDER.
              equals("Xe-xe");
        // it is ok to have rcurly on the same line as previous
        // statement if lcurly on the same line.
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); }
    }
}
