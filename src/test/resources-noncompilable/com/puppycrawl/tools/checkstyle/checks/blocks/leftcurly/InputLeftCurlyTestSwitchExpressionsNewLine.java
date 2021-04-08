//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = nl
 * ignoreEnums = true
 */
public class InputLeftCurlyTestSwitchExpressionsNewLine { // violation
    int howMany1(int k) { // violation
        switch (k)
        {
            case 1:
                {

            }

            case 2:
                {

            }

            case 3:
                {

            }
            default:
                {

            }
        }
        return k;
    }

    int howMany2(int k) { // violation
        return switch (k)
                {
            case 1 ->
                    {
                yield 2;
            }
            case 2 ->
                    {
                yield 3;
            }
            case 3 -> { // violation
                yield 4;
            }
            default ->
                    {
                yield k;
            }
        };
    }
}
