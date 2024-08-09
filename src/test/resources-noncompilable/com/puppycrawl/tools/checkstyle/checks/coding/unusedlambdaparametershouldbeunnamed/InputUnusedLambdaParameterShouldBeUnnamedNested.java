/*
UnusedLambdaParameterShouldBeUnnamed

*/


//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlambdaparametershouldbeunnamed;

import java.util.function.BiFunction;
import java.util.function.Function;

public class InputUnusedLambdaParameterShouldBeUnnamedNested {

    void m1() {

        BiFunction<String, String, String> function = (x, y) -> {
            // 2 violations above:
            //                  'Unused lambda parameter 'x' should be unnamed'
            //                  'Unused lambda parameter 'y' should be unnamed'
            Function<String, String> function2 = (z) -> {
                // violation above, 'Unused lambda parameter 'z' should be unnamed'
                return "a";
            };
            return "a";
        };

        function = (_, _) -> {
            Function<String, String> function2 = (z) -> {
                // violation above, 'Unused lambda parameter 'z' should be unnamed'
                return "a";
            };
            return "a";
        };

        function = (_, _) -> {
            Function<String, String> function2 = (_) -> {
                return "a";
            };
            return "a";
        };


        function = (x, y) -> {
            Function<String, String> function2 = (z) -> {
                // violation above, 'Unused lambda parameter 'z' should be unnamed'
                return x;
            };
            int z = 0;
            return y + z;
        };

        function = (x, y) -> {
            // violation above, 'Unused lambda parameter 'y' should be unnamed'
            BiFunction<String, String, String> function2 = (z, w) -> {
                // violation above, 'Unused lambda parameter 'z' should be unnamed'
                return x + w;
            };
            int z = 0;
            return "" + z;
        };

        function = (x, y) -> {
            BiFunction<String, String, String> function2 = (z, w) -> {
                // 2 violations above:
                //                  'Unused lambda parameter 'z' should be unnamed'
                //                  'Unused lambda parameter 'w' should be unnamed'
                return x + y;
            };
            int z = 0;
            return "" + z;
        };

        function = (x, y) -> {
            BiFunction<String, String, String> function2 = (z, w) -> {
                // violation above, 'Unused lambda parameter 'w' should be unnamed'
                return x + y + z;
            };
            String w = "a";
            return w;
        };
    }

    void TypedLambdaParameter() {
        BiFunction<String, String, String> function = (String x, String y) -> {
            // violation above, 'Unused lambda parameter 'y' should be unnamed'
            Function<Integer, String> function2 = (Integer z) -> {
                // violation above, 'Unused lambda parameter 'z' should be unnamed'
                return "a" + x;
            };
            return "a";
        };

        function = (String x, String y) -> {
            // violation above, 'Unused lambda parameter 'x' should be unnamed'
            Function<Integer, String> function2 = (Integer z) -> {
                return "a" + z;
            };
            return y;
        };

        function = (String x, String y) -> {
            Function<Integer, String> function2 = (Integer _) -> {
                return "a" + x + y;
            };
            return x;
        };
    }
}
