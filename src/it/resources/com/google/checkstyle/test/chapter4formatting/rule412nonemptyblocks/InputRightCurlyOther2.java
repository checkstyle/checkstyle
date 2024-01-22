package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputRightCurlyOther2
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
                } else if (x < 0) {  //ok

                    ;
                } // warning
                else
                {
                    break;
                }//ok
                switch (a)
                {
                case 0:
                    break;
                default:
                    break;
                } //ok
            } // warning
            catch (Exception e)
            {
                break;
            }//ok
        }//ok

        synchronized (this)
        {
            do
            {
                x = 2;
            } while (x == 2); //ok
        }//ok

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }//ok


        if (System.currentTimeMillis() > 1000)
            return 1;
        else
            return 2;
    }//ok


    static
    {
        int x = 1;
    }//ok

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
            flag = !flag; } System. // warn
              identityHashCode("Xe-xe");


        if (flag) { System.identityHashCode("some foo"); }
    } //ok
} //ok

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtorAlone
{
    int i;
    public FooCtorAlone()
    {
        i = 1;
    }} // warn

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethodAlone
{
    public void fooMethod()
    {
        int i = 1;
    }} // warn

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerAlone
{
    class InnerFoo
    {
        public void fooInnerMethod ()
        {

        }
    }}

class EnumContainerAlone {
    private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
}

class WithArraysAlone {
    String[] s = {""};
    String[] empty = {};
    String[] s1 = {
        "foo", "foo",
    }; // ok
    String[] s2 =
        {
            "foo", "foo",
        }; // ok
    String[] s3 =
        {
            "foo",
            "foo",
        }; // ok
    String[] s4 =
        {"foo", "foo"}; // ok
}

class Interface {
    public @interface TestAnnotation {}

    public @interface TestAnnotation1 { String someValue(); }

    public @interface TestAnnotation2 {
        String someValue();}

    public @interface TestAnnotation3 {
        String someValue();
    } //ok

    public @interface TestAnnotation4 { String someValue();
    } //ok
}

enum TestEnum {}

enum TestEnum1 { SOME_VALUE; }

enum TestEnum2 {
    SOME_VALUE;}

enum TestEnum3 {
    SOME_VALUE;
}

enum TestEnum4 { SOME_VALUE;
}
