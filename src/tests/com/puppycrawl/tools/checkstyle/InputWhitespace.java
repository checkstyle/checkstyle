////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Class for testing whitespace issues.
 * error missing author tag
 **/
class InputWhitespace
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
        b -=- 1; // Ignore 2
        b = b ++; // Ignore 1
        b = ++ b; // Ignore 1
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
    
}
