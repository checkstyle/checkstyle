/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheckNestedClasses {
    void test(Object o) {
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

    void local(Object o) {
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

    void lambda(Object o) {
        if (o instanceof String s) {
            Runnable r = () -> {
                String s = "lambda local";
                s = "lambda local assignment";
            };
            r.run();
        }
    }

    void nestedEnum(Object o) {
        if (o instanceof String s) {
            enum LocalEnum {
                VALUE;
                String s;
                void set() {
                    s = "enum field";
                }
            }
            LocalEnum.VALUE.set();
        }
    }

    void nestedRecord(Object o) {
        if (o instanceof String s) {
            record LocalRecord(String s) {
                void set() {
                    s = "record component";
                }
            }
            new LocalRecord("test").set();
        }
    }

    void stillReportsActualReassignment(Object o) {
        if (o instanceof String s) {
            s = "actual reassignment"; // violation "Assignment of pattern variable 's' is not allowed."
        }
    }
}
