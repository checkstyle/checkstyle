/*
RightCurly
option = invalid_option
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyTestInvalidOption
{
    void foo() throws InterruptedException
    {

            try
            {

            }
            catch (Exception e)
            {
                return;
            }

        }

    }

class UniqEmptyClassTestInvalidOption {private int a;}
