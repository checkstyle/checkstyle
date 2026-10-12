/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck6 {

    void testNonObjBlockWithSameNamedVariable(Object o) {
        if (o instanceof String s) {
            Runnable r = new Runnable() {
                String _ = "unnamed";

                void s() {}

                @Override
                public void run() {
                    String s = "shadow";
                    s = "reassigned";
                }
            };
            r.run();
        }
    }

    void testLocalClassFieldAfterMethod(Object o) {
        if (o instanceof String s) {
            class Local {
                void set() {
                    s = "field of local class";
                }

                String s;
            }
            new Local().set();
        }
    }

    void testAnonymousClassWithFieldAfterMethod(Object o) {
        if (o instanceof String s) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    s = "field of anonymous class";
                }

                private String s;
            };
            r.run();
        }
    }

    void testElseBranchAssignmentNotFlagged(Object o) {
        class Holder {
            String s;

            void method(Object obj) {
                if (obj instanceof String s) {
                    s = "reassigned";
                    // 1 violations above:
                    //    "Assignment of pattern variable 's' is not allowed."
                }
                else {
                    s = "else";
                }
            }
        }
    }

    void testAssignmentInConditionDoesNotViolateExtendedScope(Object o) {
        class Holder {
            String s;

            void method(Object obj) {
                if ((s = "cond").isEmpty() && !(obj instanceof String s)) {
                    return;
                }
                s = "reassigned";
                // 1 violations above:
                //    "Assignment of pattern variable 's' is not allowed."
            }
        }
    }
}
