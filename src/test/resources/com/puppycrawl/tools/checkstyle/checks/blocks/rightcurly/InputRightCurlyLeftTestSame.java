/*
RightCurly
option = (default)SAME
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, ANNOTATION_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyLeftTestSame
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
                } // violation ''}' at column 17 should be on the same line as .*/else'
                else if (x < 0) {
                    ;
                } // violation ''}' at column 17 should be on the same line as .*/else'
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
            } // violation ''}' at column 13 should be on the same line as .*/catch'
            catch (Exception e)
            {
                break;
            } // violation ''}' at column 13 should be on the same line as .*/finally'
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
            } while (x == 2); // ok
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
    }; // violation ''}' at column 5 should be alone on a line'

    void method2()
    {
        boolean flag = true;
        if (flag) {
            System.identityHashCode("heh");
            flag = !flag; } String. // violation ''}' at column 27 should have line break before'
                CASE_INSENSITIVE_ORDER.equals("Xe-xe");
        // it is ok to have rcurly on the same line as previous
        // statement if lcurly on the same line.
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); } // ok
    }
}

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtorTestSame
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
class FooMethodTestSame
{
        public void fooMethod()
    {
                int i = 1;
    }} // ok

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerTestSame
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
class Absent_CustomFieldSerializer3TestSame {

    public static void serialize() {} // ok
}

class Absent_CustomFieldSerializer4TestSame
{
    public void Absent_CustomFieldSerializer4() {} // ok
}

class EmptyClass2TestSame {} // ok

interface EmptyInterface3TestSame {} // ok

class ClassWithStaticInitializersTestSame
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
        } catch (RuntimeException e) { // ok
            new Object();
        } catch (Exception e) { // ok
            // comment
        } catch (Throwable e) { // ok
        } finally { // ok
            // comment
        }

        do {
        } while (true); // ok
    }

    public void codeAfterLastRightCurly() {
        while (new Object().equals(new Object())) {
        }; // violation ''}' at column 9 should be alone on a line'
        for (int i = 0; i < 1; i++) { ; }; // violation ''}' at col.* 41 should be alone on a line'
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
