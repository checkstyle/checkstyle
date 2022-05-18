/*
RightCurly
option = (default)SAME
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, ANNOTATION_DEF, ENUM_DEF, INSTANCE_INIT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestSame2
{

    int func() throws InterruptedException
    {
        int x = 1;
        while (true)
        {
            try
            {
                if (x > 0)
                {
                    break;
                }   else if (x < 0) {  // ok
                    ;
                }  // violation ''}' at column 17 should be on the same line as .*/else'
                else
                {
                    break;
                }
            } // violation ''}' at column 13 should be on the same line as .*/catch'
            catch (Exception e)
            {
                break;
            } // violation ''}' at column 13 should be on the same line as .*/finally'
            finally
            {
                break;
            }
        }


        do
        {     x = 2; // ok
        } while (x == 2); // ok

        if (x == 3) {
            int i = 4;
        } String s = ""; // violation ''}' at column 9 should be alone on a line'

        if (x < 3)
            return 1;
        else return 0;
    }

    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
        }    }
    ); // ok

    void func2() {
        int y = 5, z = 4;
        if (y > 0) { z = 7; y = 9;}  // ok

        if (y < 0) {z = 3;} else { z = 6; y = 9;
        } // ok

        if (y < 0) {
            z = 3;
        } else { z = 6; y = 9; } // ok
    }
}
