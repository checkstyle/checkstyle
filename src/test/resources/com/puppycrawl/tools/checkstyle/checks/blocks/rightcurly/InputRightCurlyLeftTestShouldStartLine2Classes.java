/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = CLASS_DEF, METHOD_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a method.
*/
class InputRightCurlyLeftTestShouldStartLine2Classes
{
        public void fooMethod()
    {
                int i = 1;
    }}
// 2 violations above:
// ''}' at column 5 should be alone on a line.'
// ''}' at column 6 should be alone on a line.'

/**
* Test input for closing brace if that brace terminates
* a statement or the body of a named class.
*/
class FooInnerTestShouldStartLine2
{
        class InnerFoo
    {
                public void fooInnerMethod ()
        {

                }
    }}
// 2 violations above:
// ''}' at column 5 should be alone on a line.'
// ''}' at column 6 should be alone on a line.'

/**
 * False positive
 *
 */
class Absent_CustomFieldSerializer3TestShouldStartLine2 {

    public static void serialize() {}
}

class Absent_CustomFieldSerializer4TestShouldStartLine2
{
    public void Absent_CustomFieldSerializer4() {}
}

class EmptyClass2TestShouldStartLine2 {}

interface EmptyInterface3TestShouldStartLine2 {}

class ClassWithStaticInitializersTestShouldStartLine2
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
        } catch (RuntimeException e) {
            new Object();
        } catch (Exception e) {
            // comment
        } catch (Throwable e) {
        } finally {
            // comment
        }

        do {
        } while (true);
    }

    public void codeAfterLastRightCurly() {
        while (new Object().equals(new Object())) {
        };
        for (int i = 0; i < 1; i++) { new Object(); };
    }

    static final java.util.concurrent.ThreadFactory threadFactory
            = new java.util.concurrent.ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(r);
        }}; // violation ''}' at column 9 should be alone on a line'

    interface Interface1
    {
        int i = 1;
        public void meth1(); } // violation ''}' at column 30 should be alone on a line'

    interface Interface2
    { int i = 1; public void meth1(); }

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        }}
    // 2 violations above:
    // ''}' at column 9 should be alone on a line.'
    // ''}' at column 10 should be alone on a line.'
}
