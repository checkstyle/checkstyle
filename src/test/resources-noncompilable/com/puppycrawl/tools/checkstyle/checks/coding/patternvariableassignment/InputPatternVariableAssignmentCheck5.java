/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck5 {

    void testMethodWithSameNameDoesNotShadow(Object o) {
        if (o instanceof String s) {
            Runnable r = new Runnable() {
                void s() {}

                @Override
                public void run() {
                    s = "reassigned";
                    // 1 violations above:
                    //    "Assignment of pattern variable 's' is not allowed."
                }
            };
            r.run();
        }
    }

    void testVariableDeclaredAfterAssignmentDoesNotShadow(Object o) {
        if (o instanceof String s) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    s = "reassigned";
                    // 1 violations above:
                    //    "Assignment of pattern variable 's' is not allowed."
                    String s = "later";
                }
            };
            r.run();
        }
    }

    void testMethodParameterShadowing(Object o) {
        if (o instanceof String s) {
            class Local {
                void method(String s) {
                    s = "param";
                }
            }
            new Local().method("test");
        }
    }

    void testCatchParameterShadowing(Object o) {
        if (o instanceof String s) {
            try {
                int x = 1;
            }
            catch (Exception s) {
                s = null;
            }
        }
    }

    void testLambdaParameterShadowing(Object o) {
        if (o instanceof String s) {
            java.util.function.Consumer<String> c = (s) -> {
                s = "lambda";
            };
            java.util.function.Consumer<String> c2 = s -> {
                s = "untyped";
            };
        }
    }

    void testLambdaOtherParameterDoesNotShadow(Object o) {
        if (o instanceof String s) {
            java.util.function.Consumer<String> c = other -> {
                s = "reassigned";
                // 1 violations above:
                //    "Assignment of pattern variable 's' is not allowed."
            };
        }
    }

    void testPatternVariableWithoutScope(Object o) {
        boolean b = o instanceof String s;
    }

    void testNoParameterLambdaDoesNotShadow(Object o) {
        if (o instanceof String s) {
            Runnable r = () -> {
                s = "reassigned";
                // 1 violations above:
                //    "Assignment of pattern variable 's' is not allowed."
            };
            r.run();
        }
    }
}
