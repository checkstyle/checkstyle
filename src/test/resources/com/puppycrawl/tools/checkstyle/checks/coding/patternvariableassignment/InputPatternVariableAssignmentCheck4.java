/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck4 {

    void testAnonymousClass(Object o) {
        if (o instanceof String s) {
            Runnable r = new Runnable() {
                private String s;

                @Override
                public void run() {
                    s = "field of anonymous class";
                }
            };
            r.run();
        }
    }

    void testLocalClass(Object o) {
        if (o instanceof String s) {
            class Local {
                String s;

                void set() {
                    s = "field of local class";
                }
            }
            new Local().set();
        }
    }

    void testNestedClassPatternVariableAssignment(Object o) {
        if (o instanceof String s) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Object innerObj = "test";
                    if (innerObj instanceof String inner) {
                        inner = "reassigned";
                        // 1 violations above:
                        //    "Assignment of pattern variable 'inner' is not allowed."
                    }
                }
            };
            r.run();
        }
    }

    void testExtendedScopeAnonymousAndLocalClass(Object o) {
        if (!(o instanceof String s)) {
            return;
        }
        Runnable r = new Runnable() {
            private String s;

            @Override
            public void run() {
                s = "field of anonymous class";
            }
        };
        r.run();
        class Local {
            String s;

            void set() {
                s = "field of local class";
            }
        }
        new Local().set();
    }
}
