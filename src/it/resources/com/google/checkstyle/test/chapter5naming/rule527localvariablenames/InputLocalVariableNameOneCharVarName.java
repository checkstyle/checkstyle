package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class InputLocalVariableNameOneCharVarName
{
    public void fooMethod()
    {
        for(int i = 1; i <10; i++) { //ok
            //some code
        }

        int i = 0;

        for(int index = 1; index < 10; index++) { //ok
            //some code
        }

        for(int I_ndex = 1; I_ndex < 10; I_ndex++) { //warn
            //some code
        }

        int index = 1;

        for(; index < 10; index++) { //ok
            //some code
        }

        for(; i < 12; i++) { //ok
            //some code
        }

        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, String> e : map.entrySet()) { //ok
            //some code
        }

        for (int a = 0, b[] = { 1 }, c[][] = { { 1 }, { 2 } }; a < 10; a++) { //ok
              // something
        }

        for(int i_ndex = 1; i_ndex < 10; i_ndex++) { //warn
            //some code
        }

        for(int ii_i1 = 1; ii_i1 < 10; ii_i1++) { //warn
            //some code
        }

        for(int $index = 1; $index < 10; $index++) { //warn
            //some code
        }

        for(int in$dex = 1; in$dex < 10; in$dex++) { //warn
            //some code
        }

        for(int index$ = 1; index$ < 10; index$++) { //warn
            //some code
        }
    }
}
