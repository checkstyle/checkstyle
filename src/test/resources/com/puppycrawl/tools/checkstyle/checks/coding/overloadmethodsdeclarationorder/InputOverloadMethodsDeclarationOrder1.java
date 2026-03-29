/*
OverloadMethodsDeclarationOrder


*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder1
{
    public void overloadMethod(int i)
    {
        //some foo code
    }

    public void overloadMethod(String s)
    {
        //some foo code
    }

    public void overloadMethod(boolean b)
    {
        //some foo code
    }

    public void fooMethod()
    {

    }

    // violation : because overloads never split
    // violation below 'All overloaded methods should be placed next to each other.'
    public void overloadMethod(String s, Boolean b, int i)
    {
        //some foo code
    }

    InputOverloadMethodsDeclarationOrder1 anonymous = new InputOverloadMethodsDeclarationOrder1()
    {
        public void overloadMethod(int i)
        {
            //some foo code
        }

        public void overloadMethod(String s)
        {
            //some foo code
        }

        public void overloadMethod(boolean b)
        {
            //some foo code
        }

        public void fooMethod()
        {

        }

        // violation : because overloads never split
        // violation below 'All overloaded methods should be placed next to each other.'
        public void overloadMethod(String s, Boolean b, int i)
        {
            //some foo code
        };

        public void overloadMethod(double d)
        {
          // violation 2 lines above 'All overloaded methods should be placed next to each other.'
          /*
           * Explanation of violation:
           * There is a semicolon at the end of the previous method
           * which is separating these overloaded methods
           * and causing the violation.
           */
        }
    };
}

interface Fooable1
{
    public abstract void foo(int i);
    public abstract void foo(String s);
    public abstract void noFoo();

    // violation below 'All overloaded methods should be placed next to each other.'
    public abstract void foo(String s, Boolean b, int i);
}
