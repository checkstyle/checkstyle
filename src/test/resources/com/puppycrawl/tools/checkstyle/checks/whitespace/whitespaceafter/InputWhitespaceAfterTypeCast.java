/*
WhitespaceAfter
tokens = TYPECAST


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.whitespace.whitespaceafter;

/**
 * Class for testing whitespace issues.
 * violation missing author tag
 **/
class InputWhitespaceAfterTypeCast
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
        b -=- 1 + (+ b); // Ignore 2
        b = b ++ + b --; // Ignore 1
        b = ++ b - -- b; // Ignore 1
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
        Object o = (Object) new Object();
        o = (Object)o; // violation ''typecast' is not followed by whitespace'
        o = ( Object ) o;
        o = (Object)
            o;
    }

}
