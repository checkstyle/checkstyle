/*
LeftCurly
option = nl
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyTestNewLine3
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
                else if (x < 0) { // violation ''{' at column 33 should be on a new line'
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
            } while (x == 2);
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
    };

    void method2()
    {
        boolean flag = true;
        if (flag) { // violation ''{' at column 19 should be on a new line'
            System.identityHashCode("heh");
            flag = !flag; } String.CASE_INSENSITIVE_ORDER.
              equals("Xe-xe");
        // it is ok to have rcurly on the same line as previous
        // statement if lcurly on the same line.
        if (flag) { String.valueOf("ok"); } // violation ''{' at column 19 should be on a new line'
    }
}

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtorTestNewLine3
{
        int i;
        public void FooCtor()
    {
                i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethodTestNewLine3
{
        public void fooMethod()
    {
                int i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerTestNewLine3
{
        class InnerFoo
    {
                public void fooInnerMethod ()
        {

                }
    }}

/**
 * False positive
 *
 */
class Absent_CustomFieldSerializer3TestNewLine3 {
    // violation above ''{' at column 49 should be on a new line'
    public static void serialize() {} // Expected nothing but was "'}' should be alone on a line."
}

class Absent_CustomFieldSerializer4TestNewLine3
{
    public void Absent_CustomFieldSerializer4() {}
}

class EmptyClass2TestNewLine3 {}

interface EmptyInterface3TestNewLine3 {}

class ClassWithStaticInitializersTestNewLine3
{
    static { // violation ''{' at column 12 should be on a new line'
    }
    static
    {}

    static class Inner
    {
        static { // violation ''{' at column 16 should be on a new line'
            int i = 1;
        }
    }

}
