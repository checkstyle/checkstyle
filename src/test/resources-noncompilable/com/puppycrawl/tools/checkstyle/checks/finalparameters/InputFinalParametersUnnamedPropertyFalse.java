/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = false
tokens = METHOD_DEF, CTOR_DEF, LITERAL_CATCH, FOR_EACH_CLAUSE

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

import java.util.PriorityQueue;
import java.util.Queue;

public class InputFinalParametersUnnamedPropertyFalse {

    void testUnnamedCatchParameter() {
        try {
            throw new Exception();
        } catch (Exception _) {  // violation,'Parameter _ should be final.'

        }
        try {
            int x = 1/0;
        } catch (Exception __) { // violation,'Parameter __ should be final.'

        }
        try {
            int x = 1/0;
        } catch (Exception _e) { // violation,'Parameter _e should be final.'
        }

        try {
            int x = 1/0;
        } catch (Exception e_) { // violation,'Parameter e_ should be final.'
        }
    }

    void testUnnamedForEachParameter() {
        Queue<Integer> q = new PriorityQueue<>();
        q.add(1);
        q.add(2);
        for (Integer _ : q) {  // violation,'Parameter _ should be final.'
            var _ = q.poll();
        }
        for (Integer __ : q) { // violation,'Parameter __ should be final.'
            var _ = q.poll();
        }
        for (Integer _i : q) { // violation,'Parameter _i should be final.'
            var _ = q.poll();
        }
        for (Integer i_ : q) {  // violation,'Parameter i_ should be final.'
           var _ = q.poll();
        }
    }
}
