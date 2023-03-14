/*
RightCurly
option = (default)same
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestWithoutFinally
{
    void foo() throws InterruptedException
    {

        try
        {

        } // violation ''}' at col.* 9 should be on the same line as .*/catch'
        catch (Exception e)
        {
            return;
        }

        }

    }

class UniqEmptyClassTestWithoutFinally {private int a;} // ok
