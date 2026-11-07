/*
TypecastParenPad
option = SPACE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typecastparenpad;

class InputTypecastParenPadWhitespaceTestSpace
{
    /** method **/
    void method2()
    {
        synchronized(this) {
        }
        try{
        }
        catch(RuntimeException e){
        }
    }

    /** test WS after void return */
    private void fastExit()
    {
        boolean complicatedStuffNeeded = true;
        if( !complicatedStuffNeeded )
        {
            return; // should not complain about missing WS after return
        }
        else
        {
            // do complicated stuff
        }
    }

    /** test WS after non void return
     @return 2
    */
    private int nonVoid()
    {
        if ( true )
        {
            return(2); // should complain about missing WS after return
        }
        else
        {
            return 2; // this is ok
        }
    }

    /** test casts **/
    private void testCasts()
    {
        Object o = (Object) new Object();
        // 2 violations above:
        // ''(' is not followed by whitespace.'
        // '')' is not preceded with whitespace.'
        o = (Object)o;
        // 2 violations above:
        // ''(' is not followed by whitespace.'
        // '')' is not preceded with whitespace.'
        o = ( Object ) o;
        // 2 violations 3 lines below:
        // ''(' is not followed by whitespace.'
        // '')' is not preceded with whitespace.'
        o = (Object)
            o;
    }
}

class SpecialCasesInForLoop_TypecastParenPad2
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
    for ( ; i < 5; i++ ) {
    //   ^ whitespace
    }
        for (int anInt : getSomeInts()) {
            //Should be ignored
        }
    }

    int[] getSomeInts() {
        int i = (int) ( 2 / 3 );
        // 2 violations above:
        // ''(' is not followed by whitespace.'
        // '')' is not preceded with whitespace.'
        return null;
    }

    public void testNullSemi() {
        return ;
    }

    public void register(Object obj) { }

    public void doSomething(String args[]) {
        register(boolean[].class);
        register( args );
    }

    public void parentheses() {
        testNullSemi
(
)
;
    }
}
