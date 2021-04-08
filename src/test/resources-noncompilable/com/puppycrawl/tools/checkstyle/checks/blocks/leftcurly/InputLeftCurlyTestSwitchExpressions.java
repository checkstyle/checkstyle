//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = eol
 * ignoreEnums = true
 */
public class InputLeftCurlyTestSwitchExpressions {
    int howMany1(int k) {
        switch (k)
        { // violation
            case 1:
                { // violation

            }

            case 2:
                { // violation

            }

            case 3:
                { // violation

            }
            default:
                {   // violation

            }
        }
        return k;
    }

    int howMany2(int k) {
        return switch (k)
                { // violation
            case 1 ->
                    {// violation
                yield 2;
            }
            case 2 ->
                    {// violation
                yield 3;
            }
            case 3 ->
                    {// violation
                yield 4;
            }
            default ->
                    {// violation
                yield k;
            }
        };
    }
}
