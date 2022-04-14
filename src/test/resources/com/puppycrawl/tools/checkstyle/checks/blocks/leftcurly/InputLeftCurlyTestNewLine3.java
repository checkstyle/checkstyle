/*
LeftCurly
option = NL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyTestNewLine3
{ // ok
    /** @see test method **/
    int foo() throws InterruptedException
    { // ok
        int x = 1;
        int a = 2;
        while (true)
        { // ok
            try
            { // ok
                if (x > 0)
                { // ok
                    break;
                }
                else if (x < 0) { // violation ''{' at column 33 should be on a new line'
                    ;
                }
                else
                { // ok
                    break;
                }
                switch (a)
                { // ok
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e)
            { // ok
                break;
            }
            finally
            { // ok
                break;
            }
        }

        synchronized (this)
        { // ok
            do
            { // ok
                x = 2;
            } while (x == 2);
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        { // ok
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
    { // ok
        int x = 1; // should not require any javadoc
    }



    public enum GreetingsEnum
    { // ok
        HELLO,
        GOODBYE
    };

    void method2()
    { // ok
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
{ // ok
        int i;
        public void FooCtor()
    { // ok
                i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethodTestNewLine3
{ // ok
        public void fooMethod()
    { // ok
                int i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerTestNewLine3
{ // ok
        class InnerFoo
    { // ok
                public void fooInnerMethod ()
        { // ok

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
{ // ok
    public void Absent_CustomFieldSerializer4() {}
}

class EmptyClass2TestNewLine3 {}

interface EmptyInterface3TestNewLine3 {}

class ClassWithStaticInitializersTestNewLine3
{ // ok
    static { // violation ''{' at column 12 should be on a new line'
    }
    static
    {}

    static class Inner
    { // ok
        static { // violation ''{' at column 16 should be on a new line'
            int i = 1;
        }
    }

}
