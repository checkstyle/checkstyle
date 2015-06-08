package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.io.*;

final class InputSimple
{                                      
    void toManyArgs(
    		int $arg1, //warn
    		int ar$g2, //warn
    		int arg3$, //warn
    		int a_rg4, //warn
    		int _arg5, //warn
    		int arg6_, //warn
    		int aArg7, //warn
    		int aArg8, //warn
    		int aar_g) //warn
            
    {}
}

class InputSimple2
{

    /** Some more Javadoc. */
    public void doSomething(int aaa, int abn, String aaA, 
            boolean bB) //warn
    {
        for (Object O : new java.util.ArrayList())
        {

        }
    }
}


/** Test enum for member naming check */
enum MyEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMemeber */
    private int someMember;
    
    public void doEnum(int aaaL,
    		long llll_llll, //warn
            boolean bB) {} //warn 
}
