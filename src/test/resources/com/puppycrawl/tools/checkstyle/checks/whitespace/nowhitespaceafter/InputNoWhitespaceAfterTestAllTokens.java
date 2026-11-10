/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, \
         TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, METHOD_REF


*/

package com . puppycrawl // violation ''.' is followed by whitespace.'
    .tools. // violation ''.' is followed by whitespace.'
    checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterTestAllTokens
{
    /** ignore assignment **/
    private int mVar1=1;
    /** ignore assignment **/
    private int mVar2 =1;
    /** Should be ok **/
    private int mVar3 = 1;

    /** method **/
    void method1()
    {
        final int a = 1;
        int b= 1; // Ignore 1
        b=1; // Ignore 1
        b+=1; // Ignore 1
        b -=- 1 + (+ b);
        // 2 violations above:
        //          ''-' is followed by whitespace.'
        //          'is followed by whitespace.'
        b = b ++ + b --; // Ignore 1
        b = ++ b - -- b;
        // 2 violations above:
        //          'is followed by whitespace.'
        //          ''--' is followed by whitespace.'
    }

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

    /**
       skip blank lines between comment and code,
       should be ok
    **/


    private int mVar4 = 1;


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
        Object o = (Object) new Object(); // violation '')' is followed by whitespace.'
        o = (Object)o;
        o = ( Object ) o; // violation '')' is followed by whitespace.'
        o = (Object) // violation '')' is followed by whitespace.'
            o;
    }

    /** test questions **/
    private void testQuestions()
    {
        boolean b = (1 == 2)?true:false;
        b = (1==2) ? false : true;
    }

    /** star test **/
    private void starTest()
    {
        int x = 2 *3* 4;
    }
}
