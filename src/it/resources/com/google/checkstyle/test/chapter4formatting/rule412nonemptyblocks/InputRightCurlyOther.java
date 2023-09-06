package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputRightCurlyOther
{
    /** @see test method **/
    int foo() throws InterruptedException
    {
        int x = 1;
        int a = 2;
        while (true)
        {
            try
            {
                if (x > 0)
                {
                    break;
                } else if (x < 0) {  

                    ;
                } //warn
                else
                {
                    break;
                }
                switch (a)
                {
                case 0:
                    break;
                default:
                    break;
                } 
            } //warn
            catch (Exception e)
            {
                break;
            }
        }

        synchronized (this)
        {
            do
            {
                x = 2;
            } while (x == 2); 
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }


        if (System.currentTimeMillis() > 1000)
            return 1;
        else
            return 2;
    }


    static
    {
        int x = 1;
    }

    public enum GreetingsEnum
    {
        HELLO,
        GOODBYE
    }; 

    void method2()
    {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } System. //warn
              identityHashCode("Xe-xe");


        if (flag) { System.identityHashCode("some foo"); }
    } 
} 

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtor
{
    int i;
    public FooCtor()
    {
        i = 1;
    }} // warn

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethod
{
    public void fooMethod()
    {
        int i = 1;
    }} // warn

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInner
{
    class InnerFoo
    {
        public void fooInnerMethod ()
        {

        }
    }} 

class EnumContainer {
    private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS } 
}

class WithArrays {
    String[] s = {""}; 
    String[] empty = {}; 
    String[] s1 = {
        "foo", "foo",
    }; 
    String[] s2 =
        {
            "foo", "foo",
        }; 
    String[] s3 =
        {
            "foo",
            "foo",
        }; 
    String[] s4 =
        {"foo", "foo"}; 
}
