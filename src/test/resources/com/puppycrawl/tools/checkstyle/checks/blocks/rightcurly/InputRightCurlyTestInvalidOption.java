package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * Config:
 * option = invalid_option
 */
class InputRightCurlyTestInvalidOption
{
    void foo() throws InterruptedException
    {

            try
            {

            } // ok
            catch (Exception e)
            {
                return;
            }

        }

    }

class UniqEmptyClassTestInvalidOption {private int a;} // ok
