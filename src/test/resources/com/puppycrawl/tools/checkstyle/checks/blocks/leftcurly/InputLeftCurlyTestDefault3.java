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
{ // violation
    /** @see test method **/
    int foo() throws InterruptedException
    { // violation
        int x = 1;
        int a = 2;
        while (true)
        { // violation
            try
            { // violation
                if (x > 0)
                { // violation
                    break;
                }
                else if (x < 0) { // ok
                    ;
                }
                else
                { // violation
                    break;
                }
                switch (a)
                { // violation
                case 0:
                    break;
                default:
                    break;
                }
            }
            catch (Exception e)
            { // violation
                break;
            }
            finally
            { // violation
                break;
            }
        }

        synchronized (this)
        { // violation
            do
            { // violation
                x = 2;
            } while (x == 2);
        }

        this.wait(666
                 ); // Bizarre, but legal

        for (int k = 0; k < 1; k++)
        { // violation
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
    { // violation
        int x = 1; // should not require any javadoc
    }



    public enum GreetingsEnum
    { // violation
        HELLO,
        GOODBYE
    };

    void method2()
    { // violation
        boolean flag = true;
        if (flag) { // ok
            System.identityHashCode("heh");
            flag = !flag; } String.CASE_INSENSITIVE_ORDER.
              equals("Xe-xe");
        // it is ok to have rcurly on the same line as previous
        // statement if lcurly on the same line.
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); } // violation
    }
}

/**
 * Test input for closing brace if that brace terminates
 * a statement or the body of a constructor.
 */
class FooCtor
{ // violation
        int i;
        public FooCtor()
    { // violation
                i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class FooMethod
{ // violation
        public void fooMethod()
    { // violation
                int i = 1;
    }}

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInner
{ // violation
        class InnerFoo
    { // violation
                public void fooInnerMethod ()
        { // violation

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
{ // violation
    public Absent_CustomFieldSerializer4() {}
}

class EmptyClass2 {}

interface EmptyInterface3 {}

class ClassWithStaticInitializers
{ // violation
    static { // ok
    }
    static
    {}

    static class Inner
    { // violation
        static { // ok
            int i = 1;
        }
    }

}
