/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableMultipleAndNestedConditions {

    // False positive
    // https://github.com/checkstyle/checkstyle/issues/3186
    void method() {
        for (int i = 0; i < 2; i++) {
            final Object converter = new Object();
            final String type = getType();
            Object value;

            if ("s1".equals(type)) {
                if (getCondition(1)) {
                    value = getValue(1);
                }
                else {
                    continue;
                }
            }
            else if ("s2".equals(type)) {
                if (getCondition(2)) {
                    value = getValue(2);
                }
                else {
                    continue;
                }
            }
            else {
                continue;
            }

            if (converter != null) {
                value = /* converter. */getValue(1, type, value);
            }
        }
    }

    // False positive
    // https://github.com/checkstyle/checkstyle/issues/3186
    void method2() {
        for (int i = 0; i < 2; i++) {
            final Object converter = new Object();
            final Object element = new Object();
            String name;

            if (getCondition(1)) {
                name = "1";
            } else if (getCondition(2)) {
                name = "2";
            } else {
                continue;
            }

            if (converter != null) {
                name = /* converter. */getName(element, name);

                if (name == null)
                    continue;
            }
        }
    }

    public Object getValue(int i) {
        return null;
    }
    public Object getValue(int i, String type, Object value) {
        return value;
    }
    public boolean getCondition(int i) {
        return true;
    }
    public String getType() {
        return "s1";
    }
    private String getName(Object element, String name) {
        return "s";
    }
}
