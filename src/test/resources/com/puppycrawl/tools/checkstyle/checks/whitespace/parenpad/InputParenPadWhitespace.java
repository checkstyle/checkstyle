/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.whitespace.parenpad;

class InputParenPadWhitespace
{
    private void fastExit()
    {
        boolean complicatedStuffNeeded = true;
        if( !complicatedStuffNeeded )
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        {
            return; // should not complain about missing WS after return
        }
        else
        {
            // do complicated stuff
        }
    }

    private int nonVoid()
    {
        if ( true )
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        {
            return(2); // should complain about missing WS after return
        }
        else
        {
            return 2; // this is ok
        }
    }

    private void testCasts()
    {
        Object o = (Object) new Object(); // some comment
        o = (Object)o;
        o = ( Object ) o; // some comment
        o = (Object)
            o; // some comment
    }

    private void testQuestions()
    {
        boolean b = (1 == 2)?true:false;
        b = (1==2) ? false : true;
    }
}

class SpecialCasesInForLoop
{
    void forIterator()
    {
        // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
        for (int i = 0; i++ < 5;) {
	    //                  ^ no whitespace
	}

        // bug 895072
	// avoid conflict between ParenPad(space) and NoWhiteSpace before ';'
	int i = 0;
	for ( ; i < 5; i++ ) { // violation '')' is preceded with whitespace.'
	//   ^ whitespace
	}
        for (int anInt : getSomeInts()) {
            //Should be ignored
        }
    }

    int[] getSomeInts() {
        int i = (int) ( 2 / 3 );
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
        return null;
    }

    public void testNullSemi() {
        return ;
    }

    public void register(Object obj) { }

    public void doSomething(String args[]) {
        register(boolean[].class);
        register( args );
            // 2 violations above:
            //           ''(' is followed by whitespace.'
            //           '')' is preceded with whitespace.'
    }

    public void parentheses() {
        testNullSemi
(
)
;
    }

    public static void testNoWhitespaceBeforeEllipses(String ... args) {
    }
}
