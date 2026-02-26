/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

class InputHiddenField2Misc {

    abstract class InputHiddenFieldBug10845122 {
        String x;
        public abstract void methodA(String x); // violation, ''x' hides a field'
    }

    class Bug33709462 {
        private int xAxis;
        public void setxAxis(int xAxis) { // violation, ''xAxis' hides a field'
            this.xAxis = xAxis;
        }
    }

    class OneLetterField2 {
        int i;
        void setI(int i) { // violation, ''i' hides a field'
            this.i = i;
        }
        enum Inner {}
    }

    class DuplicateFieldFromPreviousClass2 {
        public void method() {
            int i = 0;
        }
    }

    class NestedEnum2 {
        enum Test {
            A, B, C;
            int i;
        }
        void method(int i) {}
    }
}
