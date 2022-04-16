/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

class InputLeftCurlyTestDefault3
{ // violation ''{' at column 1 should be on the previous line'
    /** @see test method **/
    int foo() throws InterruptedException
    { // violation ''{' at column 5 should be on the previous line'
        int x = 1;
        int a = 2;
        while (true)
        { // violation ''{' at column 9 should be on the previous line'
            try
            { // violation ''{' at column 13 should be on the previous line'
                if (x > 0)
                { // violation ''{' at column 17 should be on the previous line'
                    break;
                }
                else if (x < 0) { // ok
                    ;
                }
                else
                { // violation ''{' at column 17 should be on the previous line'
                    break;
                }
                switch (a)
                { // violation ''{' at column 17 should be on the previous line'
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e)
            { // violation ''{' at column 13 should be on the previous line'
                break;
            }
            finally
            { // violation ''{' at column 13 should be on the previous line'
                break;
            }
        }

        synchronized (this)
        { // violation ''{' at column 9 should be on the previous line'
            do
            { // violation ''{' at column 13 should be on the previous line'
                x = 2;
            } while (x == 2);
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        { // violation ''{' at column 9 should be on the previous line'
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
    { // violation ''{' at column 5 should be on the previous line'
        int x = 1; // should not require any javadoc
    }



    public enum GreetingsEnum
    { // violation ''{' at column 5 should be on the previous line'
        HELLO,
        GOODBYE
    };

    void method2()
    { // violation ''{' at column 5 should be on the previous line'
        boolean flag = true;
        if (flag) { // ok
            System.identityHashCode("heh");
            flag = !flag; } String.CASE_INSENSITIVE_ORDER.
              equals("Xe-xe");

        // violation below ''{' at column 19 should have line break after'
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); }
    }
}

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtor
{ // violation ''{' at column 1 should be on the previous line'
        int i;
        public FooCtor()
    { // violation ''{' at column 5 should be on the previous line'
                i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethod
{ // violation ''{' at column 1 should be on the previous line'
        public void fooMethod()
    { // violation ''{' at column 5 should be on the previous line'
                int i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInner
{ // violation ''{' at column 1 should be on the previous line'
        class InnerFoo
    { // violation ''{' at column 5 should be on the previous line'
                public void fooInnerMethod ()
        { // violation ''{' at column 9 should be on the previous line'

                }
    }}

/**
 * False positive
 *
 */
class Absent_CustomFieldSerializer3 { // ok

    public static void serialize() {} // Expected nothing but was "'}' should be alone on a line."
}

class Absent_CustomFieldSerializer4
{ // violation ''{' at column 1 should be on the previous line'
    public Absent_CustomFieldSerializer4() {}
}

class EmptyClass2 {}

interface EmptyInterface3 {}

class ClassWithStaticInitializers
{ // violation ''{' at column 1 should be on the previous line'
    static { // ok
    }
    static
    {}

    static class Inner
    { // violation ''{' at column 5 should be on the previous line'
        static { // ok
            int i = 1;
        }
    }

}
