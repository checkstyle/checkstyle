////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurly
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
class UniqEmptyClass {private int a;}
