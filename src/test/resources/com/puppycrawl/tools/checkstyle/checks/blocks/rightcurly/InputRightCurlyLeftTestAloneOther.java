/*
RightCurly
option = ALONE
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE, \
         LITERAL_FOR, LITERAL_WHILE, LITERAL_DO


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

class InputRightCurlyLeftTestAloneOther
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
        }};

    interface Interface1
    {
        int i = 1;
        public void meth1(); }

    interface Interface2
    { int i = 1; public void meth1(); }

    interface Interface3 {
        void display();
        interface Interface4 {
            void myMethod();
        }}
}
