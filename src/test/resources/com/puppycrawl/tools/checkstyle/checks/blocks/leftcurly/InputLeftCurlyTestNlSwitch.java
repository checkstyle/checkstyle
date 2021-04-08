package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
  * Config:
  * option = NL
  */
public class InputLeftCurlyTestNlSwitch
{

    public void doStuff()
    {
        int x = 1;
        switch (x)
        {
            case 0: { // violation
                break;
            }
            case (1
                + 0):
            {
                break;
            }
            case 2:
            {
                break;
            }
            default
                :
            {
                break;
            }
            case 3:
            case 4:
                x++; { // OK, standalone block
                }
                break;
            case 5:
                {
                    x++;
                }
                x++;
                break;
        }
        switch (x)
        {
            case 0
            :{ // violation
                break;
            }
            default:
                // do nothing
        }
    }

}
