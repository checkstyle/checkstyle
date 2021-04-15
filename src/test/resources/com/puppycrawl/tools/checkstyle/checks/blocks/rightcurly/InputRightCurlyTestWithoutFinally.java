package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * Config: default
 */
class InputRightCurlyTestWithoutFinally
{
    void foo() throws InterruptedException
    {

            try
            {

            } // violation
            catch (Exception e)
            {
                return;
            }

        }

    }

class UniqEmptyClassTestWithoutFinally {private int a;} // ok
