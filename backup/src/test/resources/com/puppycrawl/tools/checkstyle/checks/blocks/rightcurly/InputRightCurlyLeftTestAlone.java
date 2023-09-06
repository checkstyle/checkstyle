/*
RightCurly
option = ALONE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         LITERAL_FOR, LITERAL_WHILE, LITERAL_DO


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyLeftTestAlone
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
                }
                else if (x < 0) {
                    ;
                }
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
            }
            catch (Exception e)
            {
                break;
            }
            finally
            {
                break;
            }
        }

        synchronized (this)
        {
            do
            {
                x = 2;
            } while (x == 2); // violation ''}' at column 13 should be alone on a line'
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }

        // test input for bug reported by Joe Comuzzi
        if (System.currentTimeMillis() > 1000)
            return 1;
        else
            return 2;
    }

    // Test static initialiser
    static
    {
        int x = 1; // should not require any javadoc
    }



    public enum GreetingsEnum
    {
        HELLO,
        GOODBYE
    }; // ok

    void method2()
    {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } String. // violation ''}' at column 27 should be alone on a line'
                CASE_INSENSITIVE_ORDER.equals("Xe-xe");
        // it is ok to have rcurly on the same line as previous
        // statement if lcurly on the same line.
        if (flag) { String.valueOf(""); } // violation ''}' at column 41 should be alone on a line'
    }
}

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtorTestAlone
{
        int i;
        public void FooCtor()
    {
                i = 1;
    }} // ok

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethodTestAlone
{
        public void fooMethod()
    {
                int i = 1;
    }} // ok

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerTestAlone
{
        class InnerFoo
    {
                public void fooInnerMethod ()
        {

                }
    }} // ok

/**
 * False positive
 *
 */
class Absent_CustomFieldSerializer3TestAlone {

    public static void serialize() {} // ok
}

class Absent_CustomFieldSerializer4TestAlone
{
    public void Absent_CustomFieldSerializer4() {} // ok
}

class EmptyClass2TestAlone {} // ok

interface EmptyInterface3TestAlone {} // ok

class ClassWithStaticInitializersTestAlone
{
    static {
    }
    static
    {}

    static class Inner
    {
        static {
            int i = 1;
        }
    }

    public void emptyBlocks() {
        try {
            // comment
        } catch (RuntimeException e) { // violation ''}' at column 9 should be alone on a line'
            new Object();
        } catch (Exception e) { // violation ''}' at column 9 should be alone on a line'
            // comment
        } catch (Throwable e) { // violation ''}' at column 9 should be alone on a line'
        } finally { // violation ''}' at column 9 should be alone on a line'
            // comment
        }

        do {
        } while (true); // violation ''}' at column 9 should be alone on a line'
    }

    public void codeAfterLastRightCurly() {
        while (new Object().equals(new Object())) {
        }; // violation ''}' at column 9 should be alone on a line'
        for (int i = 0; i < 1; i++) { new Object(); };
        // violation above ''}' at column 53 should be alone on a line'
    }

    static final java.util.concurrent.ThreadFactory threadFactory
            = new java.util.concurrent.ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(r);
        }}; // ok

    interface Interface1
    {
        int i = 1;
        public void meth1(); } // ok

    interface Interface2
    { int i = 1; public void meth1(); } // ok

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        }} // ok
}
