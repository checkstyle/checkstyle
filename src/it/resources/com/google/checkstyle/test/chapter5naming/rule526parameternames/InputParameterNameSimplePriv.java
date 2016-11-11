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
