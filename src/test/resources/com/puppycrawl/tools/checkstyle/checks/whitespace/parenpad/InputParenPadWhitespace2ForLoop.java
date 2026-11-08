/*
ParenPad
option = SPACE
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.whitespace.parenpad;

class InputParenPadWhitespace2ForLoop
{
    void donBradman(Runnable aRun)
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
    {
        donBradman(new Runnable() { // violation ''(' is not followed by whitespace.'
            public void run() {
            }
        }); // violation '')' is not preceded with whitespace.'

        final Runnable r = new Runnable() {
            public void run() {
            }
        };
    }

    void rfe521323()
    {
        for (int i = 0 ; i < 5; i++) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
            //        ^ whitespace
        }
    }
}

class SpecialCasesInForLoop2
{
    void forIterator()
    {
        // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
        for (int i = 0; i++ < 5;) { // violation ''(' is not followed by whitespace.'
        //                  ^ no whitespace
    }

        // bug 895072
    // avoid conflict between ParenPad(space) and NoWhiteSpace before ';'
    int i = 0;
    for ( ; i < 5; i++ ) {
    //   ^ whitespace
    }
        for (int anInt : getSomeInts()) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
            //Should be ignored
        }
    }

    int[] getSomeInts() {
        int i = (int) ( 2 / 3 );
        return null;
    }

    public void foo(java.util.List<? extends String[]> bar, Comparable<? super Object[]> baz) { }
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
    public void testNullSemi() {
        return ;
    }

    public void register(Object obj) { }
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'

    public void doSomething(String args[]) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        register(boolean[].class);
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
        register( args );
    }

    public void parentheses() {
        testNullSemi
(
)
;
    }

    public static void testNoWhitespaceBeforeEllipses(String ... args) {
            // 2 violations above:
            //           ''(' is not followed by whitespace.'
            //           '')' is not preceded with whitespace.'
    }
}
