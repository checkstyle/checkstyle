/*
LocalVariableName
format = ^[a-z]{2,}[a-zA-Z0-9]*$
allowOneCharVarInForLoop = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

import java.util.HashMap;
import java.util.Map;

class InputLocalVariableNameOneCharInitVarName
{
        public void fooMethod()
        {
                for(int i = 1; i <10; i++) {
                        int j = 1; // violation
                }
        for(int K = 1; K < 10; K++) {
            int A = 1; // violation
        }
                int i = 0; // violation

        for(int index = 1; index < 10; index++) {
                        //come code
                }

        for(int Index = 1; Index < 10; Index++) { // violation
                        //come code
                }

        int index = 1;

                for(; index < 10; index++) {
                        //come code
                }

                for(; i < 12; i++) {
                        //come code
                }

                Map<String, String> map = new HashMap<String, String>();

                for (Map.Entry<String, String> e : map.entrySet()) {
                        String a = ""; // violation
                }
                for (Map.Entry<String, String> E : map.entrySet()) {
                        String B = ""; // violation
                }
        }
}
